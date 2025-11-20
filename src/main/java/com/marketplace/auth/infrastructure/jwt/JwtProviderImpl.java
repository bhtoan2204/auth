package com.marketplace.auth.infrastructure.jwt;

import java.time.Duration;

import org.springframework.stereotype.Component;

@Component
public class JwtProviderImpl implements JwtProvider {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;

    public JwtProviderImpl(JwtBuilder jwtBuilder, JwtParser jwtParser) {
        this.jwtBuilder = jwtBuilder;
        this.jwtParser = jwtParser;
    }

    @Override
    public String generateToken(Object payload, Duration ttl, String secret) {
        return jwtBuilder.buildToken(payload, ttl, secret);
    }

    @Override
    public Object verifyToken(String token, String secret) {
        return jwtParser.parseToken(token, secret);
    }
}