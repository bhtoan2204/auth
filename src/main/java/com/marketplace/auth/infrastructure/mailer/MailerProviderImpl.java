package com.marketplace.auth.infrastructure.mailer;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailerProviderImpl implements MailerProvider {

  private final MailerProperties mailerProperties;

  public MailerProviderImpl(MailerProperties mailerProperties) {
    this.mailerProperties = mailerProperties;
  }

  @Override
  public void sendEmail(String to, String subject, String body) {
    try {
      JavaMailSender mailSender = createMailSender();

      // MimeMessage message = mailSender.createMimeMessage();
      // MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      // helper.setFrom(mailerProperties.getSmtp().getUsername());
      // helper.setTo(to);
      // helper.setSubject(subject);
      // helper.setText(body, false); // false = plain text, true = HTML

      // mailSender.send(message);
    } catch (Exception e) {
      throw new RuntimeException("Failed to send email", e);
    }
  }

  private JavaMailSender createMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(mailerProperties.getSmtp().getServer());
    mailSender.setPort(mailerProperties.getSmtp().getPort());
    mailSender.setUsername(mailerProperties.getSmtp().getUsername());
    mailSender.setPassword(mailerProperties.getSmtp().getPassword());

    // Configure additional properties for SMTP
    java.util.Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }
}
