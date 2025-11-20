package com.marketplace.auth.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.marketplace.auth.domain.entity.Permission;
import com.marketplace.auth.infrastructure.persistence.model.PermissionEntity;

@Component
public class PermissionMapper {

    public Permission toDomain(PermissionEntity entity) {
        if (entity == null) {
            return null;
        }

        return Permission.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public PermissionEntity toEntity(Permission domain) {
        if (domain == null) {
            return null;
        }

        PermissionEntity entity = PermissionEntity.builder()
                .name(domain.getName())
                .build();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        return entity;
    }
}
