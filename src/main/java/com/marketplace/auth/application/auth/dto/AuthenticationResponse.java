package com.marketplace.auth.application.auth.dto;

import java.time.Instant;

public record AuthenticationResponse(
    String accessToken,
    String refreshToken,
    Instant expiresAt
) { }
