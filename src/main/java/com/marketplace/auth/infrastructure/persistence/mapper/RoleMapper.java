package com.marketplace.auth.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.marketplace.auth.domain.entity.Role;
import com.marketplace.auth.infrastructure.persistence.model.RoleEntity;

@Component
public class RoleMapper {

    public Role toDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public RoleEntity toEntity(Role domain) {
        if (domain == null) {
            return null;
        }

        RoleEntity entity = RoleEntity.builder()
                .name(domain.getName())
                .build();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        return entity;
    }
}
