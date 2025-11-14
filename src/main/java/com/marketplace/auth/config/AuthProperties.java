package com.marketplace.auth.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    private Jwt jwt = new Jwt();
    private Token token = new Token();

    @Getter
    @Setter
    public static class Jwt {
        private String secret = "change-me";
        private String issuer = "marketplace-auth";
    }

    @Getter
    @Setter
    public static class Token {
        private Duration accessTtl = Duration.ofMinutes(15);
        private Duration refreshTtl = Duration.ofDays(7);
    }
}
