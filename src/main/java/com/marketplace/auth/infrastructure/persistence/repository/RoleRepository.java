package com.marketplace.auth.infrastructure.persistence.repository;

import com.marketplace.auth.infrastructure.persistence.model.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);

    boolean existsByName(String name);
}

