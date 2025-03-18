package com.svenruppert.expedition.planner.services.login.passwords;


import com.svenruppert.expedition.planner.services.login.passwords.checks.PasswordValidator;

public record PasswordCheckResult(boolean ok, String message, Class<? extends PasswordValidator> ruleName) {

}
