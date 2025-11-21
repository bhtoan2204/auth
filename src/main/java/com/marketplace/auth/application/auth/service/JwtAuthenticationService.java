package com.marketplace.auth.application.auth.service;

public interface JwtAuthenticationService {
  Integer extractUserId(String token);

  String extractUsername(String token);

  boolean validateToken(String token);
}