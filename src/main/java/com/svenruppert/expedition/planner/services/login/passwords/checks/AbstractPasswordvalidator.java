package com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.services.login.passwords.PasswordCheckResult;

public abstract class AbstractPasswordvalidator
    implements
    PasswordValidator,
    HasLogger {


  @Override
  public PasswordCheckResult isValid(String password) {
    if (password == null) return new PasswordCheckResult(false, "Password cannot be null", CaseValidator.class);
    return createResult(password);
  }

  protected PasswordCheckResult createResult(String password) {
    boolean ok = isValidImpl(password);
    return ok ? okMesssage() : failedMesssage();
  }

  protected abstract boolean isValidImpl(String password);

  public PasswordCheckResult okMesssage() {
    return new PasswordCheckResult(true, "Check OK", this.getClass());
  }

  public PasswordCheckResult failedMesssage() {
    return new PasswordCheckResult(false, getErrorMessage(), this.getClass());
  }

  public abstract String getErrorMessage();
}
