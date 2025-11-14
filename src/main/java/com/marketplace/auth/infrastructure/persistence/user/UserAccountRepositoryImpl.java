package com.marketplace.auth.infrastructure.persistence.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.marketplace.auth.domain.user.UserAccount;
import com.marketplace.auth.domain.user.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryImpl implements UserAccountRepository {

    private final UserAccountJpaRepository jpaRepository;

    @Override
    public Optional<UserAccount> findByIdentifier(String identifier) {
        return jpaRepository.findByUsernameIgnoreCase(identifier)
            .or(() -> jpaRepository.findByEmailIgnoreCase(identifier))
            .map(UserAccountMapper::toDomain);
    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        return jpaRepository.findById(id).map(UserAccountMapper::toDomain);
    }
}
