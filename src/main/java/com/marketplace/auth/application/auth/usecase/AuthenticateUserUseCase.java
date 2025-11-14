package com.marketplace.auth.application.auth.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marketplace.auth.application.auth.dto.AuthenticationRequest;
import com.marketplace.auth.application.auth.dto.AuthenticationResponse;
import com.marketplace.auth.application.auth.service.JwtTokenService;
import com.marketplace.auth.application.auth.service.RefreshTokenService;
import com.marketplace.auth.domain.auth.exception.InactiveUserException;
import com.marketplace.auth.domain.auth.exception.InvalidCredentialsException;
import com.marketplace.auth.domain.user.UserAccount;
import com.marketplace.auth.domain.user.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse handle(AuthenticationRequest request) {
        UserAccount user = userAccountRepository.findByIdentifier(request.usernameOrEmail())
            .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        if (!user.isActive() || user.isBanned()) {
            throw new InactiveUserException();
        }

        JwtTokenService.TokenResult accessToken = jwtTokenService.generate(user);
        RefreshTokenService.RefreshTokenData refreshToken = refreshTokenService.issue(user.getId());

        return new AuthenticationResponse(accessToken.getToken(), refreshToken.refreshToken(), accessToken.getExpiresAt());
    }
}
