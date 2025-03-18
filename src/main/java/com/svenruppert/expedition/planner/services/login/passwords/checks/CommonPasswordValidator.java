package com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.dependencies.core.logger.HasLogger;

import java.util.Set;

public class CommonPasswordValidator
    extends AbstractPasswordvalidator
    implements HasLogger {

  private static final Set<String> COMMON_PASSWORDS = Set.of(
      "123456", "password", "123456789", "12345678", "qwerty", "abc123"
  );

  @Override
  protected boolean isValidImpl(String password) {
    return ! COMMON_PASSWORDS.contains(password.toLowerCase());
  }

  public String getErrorMessage() {
    return "Password is too common. Please choose a more secure password.";
  }
}
