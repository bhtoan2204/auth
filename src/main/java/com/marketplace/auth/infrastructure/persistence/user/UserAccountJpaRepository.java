package com.marketplace.auth.infrastructure.persistence.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountJpaRepository extends JpaRepository<UserAccountJpaEntity, Long> {
    Optional<UserAccountJpaEntity> findByUsernameIgnoreCase(String username);
    Optional<UserAccountJpaEntity> findByEmailIgnoreCase(String email);
}
