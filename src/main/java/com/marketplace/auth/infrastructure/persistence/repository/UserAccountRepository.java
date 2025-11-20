package com.marketplace.auth.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketplace.auth.infrastructure.persistence.model.UserAccountEntity;

@Repository
public interface UserAccountRepository extends BaseRepository<UserAccountEntity, Integer> {

    Optional<UserAccountEntity> findByUsername(String username);

    Optional<UserAccountEntity> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserAccountEntity u WHERE u.isActive = :isActive")
    java.util.List<UserAccountEntity> findByActiveStatus(@Param("isActive") Boolean isActive);

    @Query("SELECT u FROM UserAccountEntity u WHERE u.isBanned = false")
    java.util.List<UserAccountEntity> findNonBannedUsers();
}
