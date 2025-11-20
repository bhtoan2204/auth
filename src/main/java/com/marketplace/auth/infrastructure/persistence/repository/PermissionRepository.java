package com.marketplace.auth.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.marketplace.auth.infrastructure.persistence.model.PermissionEntity;

@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity, Integer> {

    Optional<PermissionEntity> findByName(String name);

    boolean existsByName(String name);
}
