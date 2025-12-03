package com.marketplace.auth.infrastructure.cache_store;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

import com.marketplace.auth.infrastructure.persistence.model.UserAccountEntity;
import com.marketplace.auth.infrastructure.persistence.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class CachingUserAccountRepository implements UserAccountRepository {

  private static final String KEY_BY_ID = "user_account:id:%s";
  private static final String KEY_BY_USERNAME = "user_account:username:%s";
  private static final String KEY_BY_EMAIL = "user_account:email:%s";

  private final CacheProvider cacheProvider;
  private final Duration ttl;

  @Delegate(excludes = Overrides.class)
  private final UserAccountRepository delegate;

  private interface Overrides {
    Optional<UserAccountEntity> findById(Integer id);

    Optional<UserAccountEntity> findByUsername(String username);

    Optional<UserAccountEntity> findByEmail(String email);

    <S extends UserAccountEntity> S save(S entity);

    void deleteById(Integer id);

    void delete(UserAccountEntity entity);
  }

  @Override
  public Optional<UserAccountEntity> findById(Integer id) {
    return readThroughCache(buildKey(KEY_BY_ID, id), () -> delegate.findById(id));
  }

  @Override
  public Optional<UserAccountEntity> findByUsername(String username) {
    return readThroughCache(buildKey(KEY_BY_USERNAME, username),
        () -> delegate.findByUsername(username));
  }

  @Override
  public Optional<UserAccountEntity> findByEmail(String email) {
    return readThroughCache(buildKey(KEY_BY_EMAIL, email),
        () -> delegate.findByEmail(email));
  }

  @Override
  public <S extends UserAccountEntity> S save(S entity) {
    S saved = delegate.save(entity);
    cacheEntity(saved);
    return saved;
  }

  @Override
  public void deleteById(Integer id) {
    delegate.deleteById(id);
    evictById(id);
  }

  @Override
  public void delete(UserAccountEntity entity) {
    delegate.delete(entity);
    evictEntity(entity);
  }

  private Optional<UserAccountEntity> readThroughCache(String key,
      Supplier<Optional<UserAccountEntity>> loader) {
    Object cached = cacheProvider.get(key);
    if (cached instanceof UserAccountEntity) {
      return Optional.of((UserAccountEntity) cached);
    }

    Optional<UserAccountEntity> entity = loader.get();
    entity.ifPresent(this::cacheEntity);
    return entity;
  }

  private void cacheEntity(UserAccountEntity entity) {
    if (entity == null || entity.getId() == null) {
      return;
    }

    cacheProvider.set(buildKey(KEY_BY_ID, entity.getId()), entity, ttl);

    if (entity.getUsername() != null) {
      cacheProvider.set(buildKey(KEY_BY_USERNAME, entity.getUsername()), entity, ttl);
    }

    if (entity.getEmail() != null) {
      cacheProvider.set(buildKey(KEY_BY_EMAIL, entity.getEmail()), entity, ttl);
    }
  }

  private void evictById(Integer id) {
    if (id == null) {
      return;
    }
    cacheProvider.delete(buildKey(KEY_BY_ID, id));
  }

  private void evictEntity(UserAccountEntity entity) {
    if (entity == null) {
      return;
    }
    evictById(entity.getId());
    if (entity.getUsername() != null) {
      cacheProvider.delete(buildKey(KEY_BY_USERNAME, entity.getUsername()));
    }
    if (entity.getEmail() != null) {
      cacheProvider.delete(buildKey(KEY_BY_EMAIL, entity.getEmail()));
    }
  }

  private String buildKey(String pattern, Object value) {
    return pattern.formatted(value);
  }

}
