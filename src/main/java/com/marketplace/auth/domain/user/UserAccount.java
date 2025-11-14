package com.marketplace.auth.domain.user;

import lombok.Getter;

@Getter
public class UserAccount {
    private final Long id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final boolean active;
    private final boolean banned;

    private UserAccount(Long id, String username, String email, String passwordHash, boolean active, boolean banned) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.active = active;
        this.banned = banned;
    }

    public static UserAccount restore(
        Long id,
        String username,
        String email,
        String passwordHash,
        boolean active,
        boolean banned
    ) {
        return new UserAccount(id, username, email, passwordHash, active, banned);
    }
}
