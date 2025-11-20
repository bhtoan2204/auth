package com.marketplace.auth.application.user.usecase;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.marketplace.auth.application.user.command.CreateUserCommand;
import com.marketplace.auth.application.user.command.CreateUserCommandResult;
import com.marketplace.auth.application.user.service.UserService;
import com.marketplace.auth.domain.aggregate.UserAggregate;
import com.marketplace.auth.domain.entity.User;
import com.marketplace.auth.domain.value_object.EmailVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CreateUserCommandResult execute(CreateUserCommand command) {
        if (userService.existsByUsername(command.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userService.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        String salt = UUID.randomUUID().toString();
        String passwordHash = passwordEncoder.encode(command.password() + salt);

        User user = User.builder()
                .username(command.username())
                .email(new EmailVO(command.email()))
                .passwordHash(passwordHash)
                .salt(salt)
                .isActive(true)
                .isBanned(false)
                .failedLoginAttempts(0)
                .mfaEnabled(false)
                .build();

        UserAggregate aggregate = userService.createUser(user, command.userType());

        return new CreateUserCommandResult(
                aggregate.getUser().getId(),
                aggregate.getUser().getUsername(),
                aggregate.getUser().getEmail().getValue());
    }
}
