package com.posting.post.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateTokens(UserDetails user) {
        return JWT.create()                         // Cria Token JWT
                .withIssuer("Posting")              // Informa emissor
                .withSubject(user.getUsername())    // Informação única do User
                .withExpiresAt(expirationDate())    // Tempo de expiração
                .sign(Algorithm.HMAC256(secret));   // Assina token com Secret Key
    }

    public String validateToken(String token) {

        try {
            return JWT.require(Algorithm.HMAC256(secret)) // Garante autenticidade própria
                    .withIssuer("Posting")                // Informa emissor
                    .build()                              // Verifica token
                    .verify(token)                        // Verifica se não expirou
                    .getSubject();                        // Informa usuário atual
        } catch (Exception e) {
            return null;
        }

    }

    public Instant expirationDate() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }

}
