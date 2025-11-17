package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private long expirationToken = 86400000;
    private long expirationRefreshToken = 604800000;
    private String secretKey = "FSDKFJDSFKSJFSDJFDSLKFJDSKLFJDFJCVJCXKVJKJGFDSKFJDFSKLJEWROEW";


    public String generateToken(Usuario user)
    {
        return buildToken(user,expirationToken);
    }

    public String generateRefreshToken(Usuario user)
    {
        return buildToken(user,expirationRefreshToken);
    }

    private String buildToken( Usuario user,long expiration) {
        return Jwts.builder().id(user.getId().toString())
                .claims(Map.of("name",user.getUsername()))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBates = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBates);
    }

    public String extractUsername(String token) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();

    }

    public boolean isValidToken(String refreshToken, Usuario user) {
        String username = extractUsername(refreshToken);
        return (username.equals(user.getEmail()) && !isTokenExpired(refreshToken));
    }

    public boolean isTokenExpired(String token)
    {
        return extractExpirationToken(token).before(new Date());
    }

    private Date extractExpirationToken(String token) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

}
