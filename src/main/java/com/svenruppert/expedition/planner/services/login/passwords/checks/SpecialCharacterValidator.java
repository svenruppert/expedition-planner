package com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.dependencies.core.logger.HasLogger;

public class SpecialCharacterValidator extends AbstractPasswordvalidator implements HasLogger {
  private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

  protected boolean isValidImpl(String password) {
    return password.chars().anyMatch(ch -> SPECIAL_CHARACTERS.indexOf(ch) >= 0);
  }

  public String getErrorMessage() {
    return "Password must contain at least one special character: " + SPECIAL_CHARACTERS;
  }
}
