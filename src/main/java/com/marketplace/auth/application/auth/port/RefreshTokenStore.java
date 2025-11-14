package com.marketplace.auth.application.auth.port;

import java.time.Duration;
import java.util.Optional;

public interface RefreshTokenStore {
    void saveToken(Long userId, String tokenValue, Duration ttl);
    Optional<String> findToken(Long userId);
    void deleteToken(Long userId);
}
