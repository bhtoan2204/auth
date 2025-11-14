package com.marketplace.auth.domain.permission;

import java.time.Instant;

import lombok.Getter;

@Getter
public final class Permission {
    private final Long id;
    private final String name;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Permission(Long id, String name, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = validateName(name);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Permission create(String name) {
        return new Permission(null, name, null, null);
    }

    public static Permission restore(Long id, String name, Instant createdAt, Instant updatedAt) {
        return new Permission(id, name, createdAt, updatedAt);
    }

    public Permission rename(String newName) {
        return new Permission(this.id, newName, this.createdAt, this.updatedAt);
    }

    private String validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Permission name must not be blank.");
        }
        return value.trim();
    }
}
