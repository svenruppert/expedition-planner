package com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.dependencies.core.logger.HasLogger;

public class LengthValidator extends AbstractPasswordvalidator implements HasLogger {
  private final int minLength;
  private final int maxLength;

  public LengthValidator(int minLength, int maxLength) {
    this.minLength = minLength;
    this.maxLength = maxLength;
  }


  @Override
  protected boolean isValidImpl(String password) {
    return password.length() >= minLength && password.length() <= maxLength;
  }

  public String getErrorMessage() {
    return "Password must be between " + minLength + " and " + maxLength + " characters.";
  }
}
