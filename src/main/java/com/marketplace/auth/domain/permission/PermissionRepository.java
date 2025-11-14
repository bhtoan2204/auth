package com.marketplace.auth.domain.permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository {
    Permission save(Permission permission);

    Optional<Permission> findById(Long id);

    List<Permission> findAll();

    void deleteById(Long id);

    boolean existsByName(String name);

    boolean existsByNameExcludingId(String name, Long id);
}
