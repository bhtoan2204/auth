package com.marketplace.auth.infrastructure.persistence.user;

import com.marketplace.auth.domain.user.UserAccount;

final class UserAccountMapper {

    private UserAccountMapper() { }

    static UserAccount toDomain(UserAccountJpaEntity entity) {
        return UserAccount.restore(
            entity.getId(),
            entity.getUsername(),
            entity.getEmail(),
            entity.getPasswordHash(),
            entity.isActive(),
            entity.isBanned()
        );
    }
}
