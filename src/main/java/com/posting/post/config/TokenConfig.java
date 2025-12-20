package com.posting.post.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.posting.post.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenConfig {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer("posting-security")
                .withClaim("id", user.getId())
                .withSubject(user.getEmail())
                .withExpiresAt(expirationAt())
                .sign(Algorithm.HMAC256(secret));
    }

    public JWTUserData validToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("posting-security")
                    .build()
                    .verify(token);

            String email = decodedJWT.getSubject();
            Long id = decodedJWT.getClaim("id").asLong();
            return new JWTUserData(id, email);

        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public Instant expirationAt() {
        return Instant.now().plus(86400, ChronoUnit.SECONDS);
    }
}
