package com.marketplace.auth.application.permission.dto;

import java.time.Instant;

import com.marketplace.auth.domain.permission.Permission;

public record PermissionResponse(Long id, String name, Instant createdAt, Instant updatedAt) {
    public static PermissionResponse from(Permission permission) {
        return new PermissionResponse(
                permission.getId(),
                permission.getName(),
                permission.getCreatedAt(),
                permission.getUpdatedAt());
    }
}
