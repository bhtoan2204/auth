package com.marketplace.auth.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.marketplace.auth.domain.entity.User;
import com.marketplace.auth.domain.value_object.EmailVO;
import com.marketplace.auth.infrastructure.persistence.model.UserAccountEntity;

@Component
public class UserMapper {

    public User toDomain(UserAccountEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail() != null ? new EmailVO(entity.getEmail()) : null)
                .passwordHash(entity.getPasswordHash())
                .salt(entity.getSalt())
                .isActive(entity.getIsActive())
                .isBanned(entity.getIsBanned())
                .failedLoginAttempts(entity.getFailedLoginAttempts())
                .lastFailedLogin(entity.getLastFailedLogin())
                .lockedUntil(entity.getLockedUntil())
                .lastLoginAt(entity.getLastLoginAt())
                .passwordChangedAt(entity.getPasswordChangedAt())
                .mfaEnabled(entity.getMfaEnabled())
                .mfaSecret(entity.getMfaSecret())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public UserAccountEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        UserAccountEntity entity = UserAccountEntity.builder()
                .username(domain.getUsername())
                .email(domain.getEmail() != null ? domain.getEmail().getValue() : null)
                .passwordHash(domain.getPasswordHash())
                .salt(domain.getSalt())
                .isActive(domain.getIsActive())
                .isBanned(domain.getIsBanned())
                .failedLoginAttempts(domain.getFailedLoginAttempts())
                .lastFailedLogin(domain.getLastFailedLogin())
                .lockedUntil(domain.getLockedUntil())
                .lastLoginAt(domain.getLastLoginAt())
                .passwordChangedAt(domain.getPasswordChangedAt())
                .mfaEnabled(domain.getMfaEnabled())
                .mfaSecret(domain.getMfaSecret())
                .build();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        return entity;
    }
}
