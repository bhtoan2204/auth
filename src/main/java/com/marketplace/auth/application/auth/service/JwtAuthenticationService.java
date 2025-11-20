package com.marketplace.auth.application.auth.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.marketplace.auth.infrastructure.jwt.JwtProperties;
import com.marketplace.auth.infrastructure.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @SuppressWarnings("unchecked")
    public Integer extractUserId(String token) {
        try {
            Object tokenData = jwtProvider.verifyToken(token, jwtProperties.getJwt().getSecret());
            if (!(tokenData instanceof Map)) {
                return null;
            }
            Map<String, Object> claims = (Map<String, Object>) tokenData;
            Object subject = claims.get("subject");
            if (subject instanceof String) {
                return Integer.valueOf((String) subject);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public String extractUsername(String token) {
        try {
            Object tokenData = jwtProvider.verifyToken(token, jwtProperties.getJwt().getSecret());
            if (!(tokenData instanceof Map)) {
                return null;
            }
            Map<String, Object> claims = (Map<String, Object>) tokenData;
            Map<String, Object> jwtClaims = (Map<String, Object>) claims.get("claims");
            return (String) jwtClaims.get("username");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            jwtProvider.verifyToken(token, jwtProperties.getJwt().getSecret());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
