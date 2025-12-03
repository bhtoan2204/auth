package com.marketplace.auth.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.marketplace.auth.infrastructure.cache_store.CacheProvider;
import com.marketplace.auth.infrastructure.cache_store.CachingUserAccountRepository;
import com.marketplace.auth.infrastructure.persistence.repository.UserAccountRepository;

@Configuration
public class CachingRepositoryConfig {

  @Bean
  @Primary
  public UserAccountRepository cachedUserAccountRepository(
      CacheProvider cacheProvider,
      @Qualifier("userAccountRepository") UserAccountRepository delegate,
      @Value("${cache.user.ttl:PT10M}") Duration ttl) {
    return new CachingUserAccountRepository(cacheProvider, ttl, delegate);
  }
}
