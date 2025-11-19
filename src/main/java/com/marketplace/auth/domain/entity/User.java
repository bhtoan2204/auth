package com.marketplace.auth.domain.entity;

import java.time.LocalDateTime;

import com.marketplace.auth.domain.value_object.EmailVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User {

    private Integer id;
    private String username;
    private EmailVO email;
    private String passwordHash;
    private String salt;
    private Boolean isActive;
    private Boolean isBanned;
    private Integer failedLoginAttempts;
    private LocalDateTime lastFailedLogin;
    private LocalDateTime lockedUntil;
    private LocalDateTime lastLoginAt;
    private LocalDateTime passwordChangedAt;
    private Boolean mfaEnabled;
    private String mfaSecret;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

