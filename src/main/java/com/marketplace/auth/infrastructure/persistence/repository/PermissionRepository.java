package com.marketplace.auth.infrastructure.persistence.repository;

import com.marketplace.auth.infrastructure.persistence.model.PermissionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity, Integer> {

    Optional<PermissionEntity> findByName(String name);

    boolean existsByName(String name);
}

