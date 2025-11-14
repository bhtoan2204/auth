package com.marketplace.auth.application.auth.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.marketplace.auth.config.AuthProperties;
import com.marketplace.auth.domain.user.UserAccount;

import lombok.Getter;

@Service
public class JwtTokenService {

    private final Algorithm algorithm;
    private final AuthProperties properties;

    public JwtTokenService(AuthProperties properties) {
        this.properties = properties;
        this.algorithm = Algorithm.HMAC256(properties.getJwt().getSecret());
    }

    public TokenResult generate(UserAccount user) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(properties.getToken().getAccessTtl());
        String token = JWT.create()
            .withIssuer(properties.getJwt().getIssuer())
            .withSubject(user.getId().toString())
            .withClaim("username", user.getUsername())
            .withClaim("email", user.getEmail())
            .withIssuedAt(Date.from(issuedAt))
            .withExpiresAt(Date.from(expiresAt))
            .sign(algorithm);
        return new TokenResult(token, expiresAt);
    }

    @Getter
    public static class TokenResult {
        private final String token;
        private final Instant expiresAt;

        public TokenResult(String token, Instant expiresAt) {
            this.token = token;
            this.expiresAt = expiresAt;
        }
    }
}
