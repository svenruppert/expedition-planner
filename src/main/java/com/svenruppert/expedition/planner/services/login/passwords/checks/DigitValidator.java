package com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.dependencies.core.logger.HasLogger;

public class DigitValidator extends AbstractPasswordvalidator implements HasLogger {

  @Override
  protected boolean isValidImpl(String password) {
    return password.chars().anyMatch(Character::isDigit);
  }

  public String getErrorMessage() {
    return "Password must contain at least one digit.";
  }

}
