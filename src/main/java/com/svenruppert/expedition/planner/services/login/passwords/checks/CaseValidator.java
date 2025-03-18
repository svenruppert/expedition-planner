package com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.dependencies.core.logger.HasLogger;

public class CaseValidator
    extends AbstractPasswordvalidator
    implements HasLogger {

  @Override
  protected boolean isValidImpl(String password) {
    boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
    boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
    return hasUppercase && hasLowercase;
  }

  public String getErrorMessage() {
    return "Password must contain both uppercase and lowercase letters.";
  }
}
