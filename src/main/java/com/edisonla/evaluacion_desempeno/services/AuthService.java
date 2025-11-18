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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired(required = true)
    private  JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.token.registration}")
    private boolean tokenRegistration;


    public TokenResponse register(RegisterRequest request) {
        var user = new Usuario();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        var saveUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(saveUser,jwtToken);
        return new TokenResponse(jwtToken,refreshToken);
    }

    public TokenResponse login (LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokedAllUserTokens(user);
        saveUserToken(user,jwtToken);
        return new TokenResponse(jwtToken,refreshToken);
    }

    public ResponseEntity<TokenResponse> refreshToken(String authHeader) {
        if(authHeader ==null || !authHeader.startsWith("Bearer "))
        {
            throw new IllegalArgumentException("Invalid Bearer token");
        }
        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractEmail(refreshToken);
        if(userEmail == null )
        {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        Usuario user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new UsernameNotFoundException(userEmail));
        if(!jwtService.isValidToken(refreshToken,user))
        {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }
        String accessToken = jwtService.generateToken(user);
        revokedAllUserTokens(user);
        saveUserToken(user,accessToken);
        return  ResponseEntity.ok(new TokenResponse(accessToken,refreshToken));

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

    private void revokedAllUserTokens(Usuario users) {
        if(tokenRegistration) {
            List<Token> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByUserId(users.getId());
            if (!validUserTokens.isEmpty()) {
                for (Token token : validUserTokens) {
                    token.setExpired(true);
                    token.setRevoked(true);
                }
                tokenRepository.saveAll(validUserTokens);
            }
        }
    }
    public Integer validate(String token) {
        if(jwtService.isTokenExpired(token))
        {
            return 1;
        }
        return 0;
    }
}
