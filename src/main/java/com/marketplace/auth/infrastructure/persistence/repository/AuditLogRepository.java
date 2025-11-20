package com.marketplace.auth.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketplace.auth.infrastructure.persistence.model.AuditLogEntity;

@Repository
public interface AuditLogRepository extends BaseRepository<AuditLogEntity, Integer> {

    @Query("SELECT a FROM AuditLogEntity a WHERE a.userAccount.id = :userId ORDER BY a.timestamp DESC")
    List<AuditLogEntity> findByUserId(@Param("userId") Integer userId);

    List<AuditLogEntity> findByActionType(String actionType);

    List<AuditLogEntity> findByTargetEntity(String targetEntity);

    @Query("SELECT a FROM AuditLogEntity a WHERE a.timestamp BETWEEN :startTime AND :endTime ORDER BY a.timestamp DESC")
    List<AuditLogEntity> findByTimestampBetween(@Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT a FROM AuditLogEntity a WHERE a.userAccount.id = :userId AND a.timestamp BETWEEN :startTime AND :endTime ORDER BY a.timestamp DESC")
    List<AuditLogEntity> findByUserIdAndTimestampBetween(@Param("userId") Integer userId,
            @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
