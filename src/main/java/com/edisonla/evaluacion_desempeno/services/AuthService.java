package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.LoginRequest;
import com.edisonla.evaluacion_desempeno.dtos.RegisterRequest;
import com.edisonla.evaluacion_desempeno.dtos.TokenResponse;
import com.edisonla.evaluacion_desempeno.entities.Token;
import com.edisonla.evaluacion_desempeno.entities.Usuario;
import com.edisonla.evaluacion_desempeno.repositories.TokenRepository;
import com.edisonla.evaluacion_desempeno.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class AuthService {

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private  UsuarioRepository userRepository;

    @Autowired
    private  TokenRepository tokenRepository;

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.token.registration}")
    private boolean tokenRegistration;


    public TokenResponse register(RegisterRequest request) {
        Usuario user = new Usuario();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        Usuario saveUser = userRepository.save(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(saveUser,refreshToken);
        return new TokenResponse(accessToken,refreshToken);
    }

    public TokenResponse login (LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        Optional<Usuario> user = userRepository.findByEmail(request.email());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        String accessToken = jwtService.generateAccessToken(user.get());
        if(tokenRegistration) {
            Optional<Token> savedRefreshToken = tokenRepository.findValidIsFalseOrRevokedIsFalseByUserId(user.get().getId());
            if (savedRefreshToken.isEmpty() || !jwtService.isValidToken(savedRefreshToken.get().getToken(), user.get())) {
                revokeUserRefreshToken(user.get());
                String newRefreshToken = jwtService.generateRefreshToken(user.get());
                saveUserToken(user.get(), newRefreshToken);
                return new TokenResponse(accessToken, newRefreshToken);
            } else {
                return new TokenResponse(accessToken, savedRefreshToken.get().getToken());
            }
        } else {
            return new TokenResponse(accessToken, jwtService.generateRefreshToken(user.get()));
        }
    }

    public TokenResponse refreshAccessToken(String expiredAccessToken, String refreshToken) {
        String userEmail = jwtService.extractEmail(expiredAccessToken);
        Optional<Usuario> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found with email: " + userEmail);
        }
        if(!jwtService.isValidToken(expiredAccessToken,user.get())) {
            throw new IllegalArgumentException("Invalid access token");
        }
        if(!jwtService.isValidToken(refreshToken,user.get())) {
            throw new IllegalArgumentException("Invalid refresh token. Must login again");
        }
        if(!userEmail.equals(jwtService.extractEmail(refreshToken))) {
            throw new IllegalArgumentException("Refresh token owner does not correspond with access token owner");
        }
        String newAccessToken = jwtService.generateAccessToken(user.get());
        return new TokenResponse(newAccessToken, refreshToken);
    }

    private void saveUserToken(Usuario user, String jwtToken) {
        if(tokenRegistration) {
            Token token = new Token();
            token.setUser(user);
            token.setToken(jwtToken);
            token.setTokenType(Token.TokenType.BEARER);
            token.setRevoked(false);
            token.setExpired(false);
            tokenRepository.save(token);
        }
    }

    private void revokeUserRefreshToken(Usuario users) {
        if(tokenRegistration) {
            Optional<Token> token = tokenRepository.findValidIsFalseOrRevokedIsFalseByUserId(users.getId());
            if (token.isPresent()) {
                token.get().setExpired(true);
                token.get().setRevoked(true);
                tokenRepository.save(token.get());
            }
        }
    }

    public Integer validate(String token) {
        if(jwtService.isTokenExpired(token)) {
            return 1;
        }
        return 0;
    }
}
