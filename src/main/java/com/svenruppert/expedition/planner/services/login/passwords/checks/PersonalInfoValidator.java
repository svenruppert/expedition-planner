package com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.dependencies.core.logger.HasLogger;

public class PersonalInfoValidator extends AbstractPasswordvalidator implements HasLogger {
  private final String username;
  private final String email;

  public PersonalInfoValidator(String username, String email) {
    this.username = username;
    this.email = email;
  }

  protected boolean isValidImpl(String password) {
    if (username != null && password.toLowerCase().contains(username.toLowerCase())) return false;
    return email == null || !password.toLowerCase().contains(email.toLowerCase());
  }

  public String getErrorMessage() {
    return "Password must not contain personal information such as username or email.";
  }

}
