package com.marketplace.auth.infrastructure.jwt;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtParser {
    public Map<String, Object> parseToken(String token, String secret) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(10)
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            return Map.of(
                    "subject", jwt.getSubject(),
                    "issuedAt", jwt.getIssuedAt(),
                    "expiresAt", jwt.getExpiresAt(),
                    "claims", jwt.getClaims());
        } catch (JWTVerificationException e) {
            System.out.println("Error: " + e.getMessage());
            throw new JWTVerificationException("Invalid token", e);
        }
    }

    public String getSubject(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);
        return decodedJWT.getSubject();
    }
}
