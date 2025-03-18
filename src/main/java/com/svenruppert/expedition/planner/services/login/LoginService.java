package com.svenruppert.expedition.planner.services.login;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.DeleteEntityResponse;
import com.svenruppert.expedition.planner.services.UpdateEntityResponse;
import com.svenruppert.expedition.planner.services.login.passwords.PasswordHasher;
import com.svenruppert.expedition.planner.services.login.passwords.SaltGenerator;

import static com.svenruppert.expedition.planner.services.login.LoginRepository.ANONYMOUS_LOGIN;


public class LoginService implements HasLogger {

  private final LoginRepository loginRepository;
  private final SaltGenerator saltGenerator;

  public LoginService(LoginRepository loginRepository, SaltGenerator saltGenerator) {
    this.loginRepository = loginRepository;
    this.saltGenerator = saltGenerator;
  }

  public UpdateEntityResponse<Login> changePassword(int uid, String passwordOLD, String passwordNEW) {
    Login login = loginRepository.userLoginByUID(uid);
    return changePassword(login.loginName(), passwordOLD, passwordNEW);
  }

  public UpdateEntityResponse<Login> changePassword(String username, String passwordOLD, String passwordNEW) {
    Login login =
        loginRepository.userLoginByLoginName(username);
    if (login.equals(ANONYMOUS_LOGIN)) {
      logger().warn("Anonymous user entry selected - will not change data");
      return new UpdateEntityResponse<>(false, "Anonymous user entry selected - will not change data", ANONYMOUS_LOGIN);
    } else {
      PasswordHasher passwordHasher = new PasswordHasher();
      String hashedPasswordOLD = passwordHasher.hashPassword(passwordOLD, login.salt());
      if (hashedPasswordOLD.equals(login.passwordHash())) {
        String saltNew = saltGenerator.generateSalt();
        String hashedPasswordNEW = passwordHasher.hashPassword(passwordNEW, saltNew);
        logger().info("setting new password for user {} : {}", username, hashedPasswordNEW);
        return loginRepository.changePassword(login.uid(), hashedPasswordNEW, saltNew);
      } else {
        logger().warn("old password hash does not match");
        return new UpdateEntityResponse<>(false, "old password hash does not match", ANONYMOUS_LOGIN);
      }
    }
  }

  public LoginResult authenticate(String username, String password) {
    Login login = loginRepository.userLoginByLoginName(username);
    if (login.equals(ANONYMOUS_LOGIN)) {
      logger().warn("Anonymous user entry selected - no login");
      return new LoginResult("Anonymous user entry selected - no login");
    } else {
      String hashedPassword = new PasswordHasher().hashPassword(password, login.salt());
      if (login.passwordHash().equals(hashedPassword)) {
        return new LoginResult(true, "Login successful", login);
      } else {
        logger().warn("password hash does not match");
        return new LoginResult("password hash does not match");
      }
    }
  }

  public CreateEntityResponse<Login> createLogin(String loginName, String password) {
    String saltNew = saltGenerator.generateSalt();
    String hashedPassword = new PasswordHasher().hashPassword(password, saltNew);
    return loginRepository.createLogin(loginName, hashedPassword, saltNew);
  }

  public Login userLoginByLoginName(String loginName) {
    return loginRepository.userLoginByLoginName(loginName);
  }

  public Login userLoginByUID(int uid) {
    return loginRepository.userLoginByUID(uid);
  }


  public DeleteEntityResponse<Login> deleteLogin(int uid) {
    Login login = loginRepository.userLoginByUID(uid);
    if (login.equals(ANONYMOUS_LOGIN)) {
      logger().warn("Anonymous login will not be deleted");
      return new DeleteEntityResponse<>(false, "Anonymous login will not be deleted", ANONYMOUS_LOGIN);
    } else {
      return loginRepository.deleteLogin(login);
    }
  }

  public UpdateEntityResponse<Login> updateLogin(Login login) {
    return loginRepository.updateLogin(login);
  }

  public CreateEntityResponse<Login> storeNewLogin(Login login) {
    return loginRepository.createLogin(login);
  }

  public CreateEntityResponse<Login> reCreateLogin(Login login) {
    return loginRepository.storeLogin(login);
  }

  public void saveRepository() {
    loginRepository.saveRepository();
  }
}
