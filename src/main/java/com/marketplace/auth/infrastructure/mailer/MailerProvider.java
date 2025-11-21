package com.marketplace.auth.infrastructure.mailer;

public interface MailerProvider {
  void sendEmail(String to, String subject, String body);
}
