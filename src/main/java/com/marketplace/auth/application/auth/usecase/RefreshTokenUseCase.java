package com.marketplace.auth.application.auth.usecase;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.auth.dto.AuthenticationResponse;
import com.marketplace.auth.application.auth.dto.RefreshTokenRequest;
import com.marketplace.auth.application.auth.service.JwtTokenService;
import com.marketplace.auth.application.auth.service.RefreshTokenService;
import com.marketplace.auth.domain.auth.exception.InvalidCredentialsException;
import com.marketplace.auth.domain.user.UserAccount;
import com.marketplace.auth.domain.user.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final RefreshTokenService refreshTokenService;
    private final UserAccountRepository userAccountRepository;
    private final JwtTokenService jwtTokenService;

    public AuthenticationResponse handle(RefreshTokenRequest request) {
        RefreshTokenService.RefreshTokenData rotatedToken = refreshTokenService.rotate(request.refreshToken());

        UserAccount user = userAccountRepository.findById(rotatedToken.userId())
            .orElseThrow(InvalidCredentialsException::new);

        JwtTokenService.TokenResult accessToken = jwtTokenService.generate(user);

        return new AuthenticationResponse(accessToken.getToken(), rotatedToken.refreshToken(), accessToken.getExpiresAt());
    }
}
