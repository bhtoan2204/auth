package com.marketplace.auth.infrastructure.cache_store;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CacheProviderImpl implements CacheProvider {

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void set(String key, Object value, Duration ttl) {
    redisTemplate.opsForValue().set(key, value, ttl);
  }

  @Override
  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }
}
