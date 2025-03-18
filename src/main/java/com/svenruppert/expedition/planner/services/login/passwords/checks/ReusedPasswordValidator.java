package com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.dependencies.core.logger.HasLogger;

import java.util.List;

public class ReusedPasswordValidator extends AbstractPasswordvalidator implements HasLogger {

  private final List<String> previousPasswords;

  public ReusedPasswordValidator(List<String> previousPasswords) {
    this.previousPasswords = previousPasswords;
  }

  protected boolean isValidImpl(String password) {
    return previousPasswords.stream().noneMatch(password::equals);
  }

  public String getErrorMessage() {
    return "Password must not match previously used passwords.";
  }

}
