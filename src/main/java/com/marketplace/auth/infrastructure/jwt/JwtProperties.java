package com.marketplace.auth.infrastructure.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "auth")
@Data
public class JwtProperties {
    private Jwt jwt = new Jwt();
    private Token token = new Token();

    @Data
    public static class Jwt {
        private String secret;
        private String issuer;
    }

    @Data
    public static class Token {
        private Duration accessTtl;
        private Duration refreshTtl;
    }
}
