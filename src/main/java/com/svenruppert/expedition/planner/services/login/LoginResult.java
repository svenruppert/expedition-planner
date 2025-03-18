package com.svenruppert.expedition.planner.services.login;


import static com.svenruppert.expedition.planner.services.login.LoginRepository.ANONYMOUS_LOGIN;

public record LoginResult(boolean success, String message, Login login) {
  public LoginResult(boolean success, String message, Login login) {
    this.success = success;
    this.message = message;
    this.login = login;
  }

  public LoginResult(boolean success, String message) {
    this(success, message, ANONYMOUS_LOGIN);
  }

  public LoginResult(String message) {
    this(false, message, ANONYMOUS_LOGIN);
  }
}
