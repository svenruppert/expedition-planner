package com.svenruppert.expedition.planner.services.auth;


import com.svenruppert.expedition.planner.services.login.LoginResult;
import com.svenruppert.expedition.planner.services.login.LoginService;
import com.svenruppert.expedition.planner.services.user.User;
import com.svenruppert.expedition.planner.services.user.UserService;

public class AuthService {
  private final LoginService loginService;
  private final UserService userService;

  public AuthService(LoginService loginService,
                     UserService userService) {
    this.loginService = loginService;
    this.userService = userService;
  }

  public User authenticateUser(String username, String password) {
    LoginResult authenticate = loginService.authenticate(username, password);
    return userService.userByLogin(authenticate.login());
  }

}
