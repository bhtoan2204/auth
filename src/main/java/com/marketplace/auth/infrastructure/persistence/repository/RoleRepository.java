package com.marketplace.auth.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.marketplace.auth.infrastructure.persistence.model.RoleEntity;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);

    boolean existsByName(String name);
}
