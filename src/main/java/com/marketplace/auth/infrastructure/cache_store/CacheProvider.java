package com.marketplace.auth.infrastructure.cache_store;

import java.time.Duration;

public interface CacheProvider {
  void set(String key, Object value, Duration ttl);

  Object get(String key);

  void delete(String key);
}
