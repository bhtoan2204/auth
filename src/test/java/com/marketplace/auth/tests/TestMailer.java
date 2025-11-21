package com.marketplace.auth.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.marketplace.auth.infrastructure.mailer.MailerProvider;

@SpringBootTest
public class TestMailer {
  @Autowired
  private MailerProvider mailerProvider;

  @Test
  public void testSendEmail() {
    try {
      mailerProvider.sendEmail("banhhaotoan2002@gmail.com", "Test",
          "Marketplace Auth");
      System.out.println("Yes");
    } catch (Exception e) {
      System.err.println("No: " + e.getMessage());
      throw e;
    }
  }
}
