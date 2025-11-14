package com.marketplace.auth.presentation.http;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.auth.application.auth.dto.AuthenticationRequest;
import com.marketplace.auth.application.auth.dto.AuthenticationResponse;
import com.marketplace.auth.application.auth.dto.RefreshTokenRequest;
import com.marketplace.auth.application.auth.service.AuthApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthHttp {

    private final AuthApplicationService authApplicationService;

    @GetMapping("/health")
    public String health() throws UnknownHostException {
        return "OK - " + InetAddress.getLocalHost().getHostAddress();
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest request) {
        return authApplicationService.login(request);
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authApplicationService.refresh(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody RefreshTokenRequest request) {
        authApplicationService.logout(request);
    }
}
