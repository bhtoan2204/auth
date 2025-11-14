package com.marketplace.auth.application.auth.service;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.auth.dto.AuthenticationRequest;
import com.marketplace.auth.application.auth.dto.AuthenticationResponse;
import com.marketplace.auth.application.auth.dto.RefreshTokenRequest;
import com.marketplace.auth.application.auth.usecase.AuthenticateUserUseCase;
import com.marketplace.auth.application.auth.usecase.LogoutUseCase;
import com.marketplace.auth.application.auth.usecase.RefreshTokenUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    public AuthenticationResponse login(AuthenticationRequest request) {
        return authenticateUserUseCase.handle(request);
    }

    public AuthenticationResponse refresh(RefreshTokenRequest request) {
        return refreshTokenUseCase.handle(request);
    }

    public void logout(RefreshTokenRequest request) {
        logoutUseCase.handle(request);
    }
}
