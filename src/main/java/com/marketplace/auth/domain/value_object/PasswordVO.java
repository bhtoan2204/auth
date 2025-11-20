package com.marketplace.auth.domain.value_object;

import lombok.Getter;

@Getter
public class PasswordVO {
  private final String value;

  public PasswordVO(String value) {
    if (value.length() < 8) {
      throw new IllegalArgumentException("Password must be at least 8 characters long");
    }
    if (!value.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
      throw new IllegalArgumentException(
          "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public boolean matches(String value) {
    return this.value.equals(value);
  }
}
