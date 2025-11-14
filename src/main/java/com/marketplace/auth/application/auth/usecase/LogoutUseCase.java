package com.marketplace.auth.application.auth.usecase;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.auth.dto.RefreshTokenRequest;
import com.marketplace.auth.application.auth.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenService refreshTokenService;

    public void handle(RefreshTokenRequest request) {
        refreshTokenService.revoke(request.refreshToken());
    }
}
