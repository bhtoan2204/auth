package com.marketplace.auth.application.auth.service;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marketplace.auth.application.user.service.UserService;
import com.marketplace.auth.domain.aggregate.UserAggregate;
import com.marketplace.auth.infrastructure.persistence.model.UserAccountEntity;
import com.marketplace.auth.infrastructure.persistence.repository.FactoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final FactoryRepository factoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResult authenticate(String username, String password) {
        UserAggregate aggregate = userService.findByUsername(username);
        if (aggregate == null || aggregate.getUser() == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        UserAccountEntity userEntity = factoryRepository.getUserAccountRepository()
                .findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(password + userEntity.getSalt(), userEntity.getPasswordHash())) {
            handleFailedLogin(userEntity);
            throw new IllegalArgumentException("Invalid username or password");
        }

        validateAccountStatus(aggregate);

        handleSuccessfulLogin(userEntity);

        String accessToken = generateToken(userEntity.getId());
        String refreshToken = generateRefreshToken(userEntity.getId());

        return new AuthResult(
                accessToken,
                refreshToken,
                Instant.now().plusSeconds(3600));
    }

    private void handleFailedLogin(UserAccountEntity userEntity) {
        int failedAttempts = userEntity.getFailedLoginAttempts() + 1;
        userEntity.setFailedLoginAttempts(failedAttempts);
        userEntity.setLastFailedLogin(LocalDateTime.now());

        if (failedAttempts >= 5) {
            userEntity.setLockedUntil(LocalDateTime.now().plusHours(1));
        }

        factoryRepository.getUserAccountRepository().save(userEntity);
    }

    private void validateAccountStatus(UserAggregate aggregate) {
        if (!aggregate.isActive()) {
            throw new IllegalArgumentException("Account is inactive");
        }

        if (aggregate.isBanned()) {
            throw new IllegalArgumentException("Account is banned");
        }

        if (aggregate.isLocked()) {
            throw new IllegalArgumentException("Account is locked");
        }
    }

    private void handleSuccessfulLogin(UserAccountEntity userEntity) {
        userEntity.setFailedLoginAttempts(0);
        userEntity.setLastLoginAt(LocalDateTime.now());
        userEntity.setLockedUntil(null);
        factoryRepository.getUserAccountRepository().save(userEntity);
    }

    private String generateToken(Integer userId) {
        return "token_" + userId + "_" + System.currentTimeMillis();
    }

    private String generateRefreshToken(Integer userId) {
        return "refresh_" + userId + "_" + System.currentTimeMillis();
    }
}

