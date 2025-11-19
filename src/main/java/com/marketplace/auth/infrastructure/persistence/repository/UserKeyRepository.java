package com.marketplace.auth.infrastructure.persistence.repository;

import com.marketplace.auth.infrastructure.persistence.model.UserKeyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserKeyRepository extends BaseRepository<UserKeyEntity, Integer> {

    @Query("SELECT uk FROM UserKeyEntity uk WHERE uk.userAccount.id = :userId")
    List<UserKeyEntity> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT uk FROM UserKeyEntity uk WHERE uk.userAccount.id = :userId AND uk.isActive = true")
    List<UserKeyEntity> findActiveKeysByUserId(@Param("userId") Integer userId);

    List<UserKeyEntity> findByKeyType(String keyType);

    Optional<UserKeyEntity> findByKeyValue(String keyValue);

    @Query("SELECT uk FROM UserKeyEntity uk WHERE uk.userAccount.id = :userId AND uk.keyType = :keyType AND uk.isActive = true")
    List<UserKeyEntity> findActiveKeysByUserIdAndKeyType(@Param("userId") Integer userId, @Param("keyType") String keyType);

    @Query("SELECT uk FROM UserKeyEntity uk WHERE uk.expiresAt IS NOT NULL AND uk.expiresAt < :currentTime")
    List<UserKeyEntity> findExpiredKeys(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT uk FROM UserKeyEntity uk WHERE uk.expiresAt IS NOT NULL AND uk.expiresAt BETWEEN :currentTime AND :futureTime")
    List<UserKeyEntity> findKeysExpiringSoon(@Param("currentTime") LocalDateTime currentTime, @Param("futureTime") LocalDateTime futureTime);
}

