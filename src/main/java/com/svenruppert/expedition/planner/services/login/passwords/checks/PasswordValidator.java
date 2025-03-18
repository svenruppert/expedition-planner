package com.svenruppert.expedition.planner.services.login.passwords.checks;


import com.svenruppert.expedition.planner.services.login.passwords.PasswordCheckResult;

public interface PasswordValidator {
  PasswordCheckResult isValid(String password);
}
