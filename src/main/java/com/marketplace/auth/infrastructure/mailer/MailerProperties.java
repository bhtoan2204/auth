package com.marketplace.auth.infrastructure.mailer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "mailer")
@Data
public class MailerProperties {
  private Smtp smtp;

  @Data
  public static class Smtp {
    private String server;
    private int port;
    private String username;
    private String password;
  }
}
