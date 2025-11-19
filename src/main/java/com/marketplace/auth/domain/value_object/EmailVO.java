package com.marketplace.auth.domain.value_object;

import lombok.Getter;

@Getter
public class EmailVO {
  private final String value;

  public EmailVO(String value) {
    if (!value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
      throw new IllegalArgumentException("Invalid email address");
    }
    this.value = value;
  }
}
