package com.marketplace.auth.infrastructure.persistence.permission;

import com.marketplace.auth.domain.permission.Permission;

final class PermissionMapper {
    private PermissionMapper() {
    }

    static PermissionJpaEntity toEntity(Permission permission) {
        PermissionJpaEntity entity = new PermissionJpaEntity();
        entity.setId(toInteger(permission.getId()));
        entity.setName(permission.getName());
        entity.setCreatedAt(permission.getCreatedAt());
        entity.setUpdatedAt(permission.getUpdatedAt());
        return entity;
    }

    static Permission toDomain(PermissionJpaEntity entity) {
        return Permission.restore(
                toLong(entity.getId()),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private static Integer toInteger(Long id) {
        if (id == null) {
            return null;
        }
        return Math.toIntExact(id);
    }

    private static Long toLong(Integer id) {
        if (id == null) {
            return null;
        }
        return id.longValue();
    }
}
