package com.marketplace.auth.domain.user;

import java.util.Optional;

public interface UserAccountRepository {
    Optional<UserAccount> findByIdentifier(String identifier);
    Optional<UserAccount> findById(Long id);
}
