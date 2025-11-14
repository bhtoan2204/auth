package com.marketplace.auth.infrastructure.cache;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.marketplace.auth.application.auth.port.RefreshTokenStore;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisRefreshTokenStore implements RefreshTokenStore {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void saveToken(Long userId, String tokenValue, Duration ttl) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key(userId), tokenValue, ttl);
    }

    @Override
    public Optional<String> findToken(Long userId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return Optional.ofNullable(ops.get(key(userId)));
    }

    @Override
    public void deleteToken(Long userId) {
        redisTemplate.delete(key(userId));
    }

    private String key(Long userId) {
        return "auth:refresh:%d".formatted(userId);
    }
}
