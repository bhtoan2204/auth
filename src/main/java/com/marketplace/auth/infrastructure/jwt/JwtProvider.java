package com.marketplace.auth.infrastructure.jwt;

import java.time.Duration;

public interface JwtProvider {
    String generateToken(Object payload, Duration ttl, String secret);

    Object verifyToken(String token, String secret);
}
