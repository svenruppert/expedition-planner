package com.svenruppert.expedition.planner.services.user;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.DeleteEntityResponse;
import com.svenruppert.expedition.planner.services.UpdateEntityResponse;
import com.svenruppert.expedition.planner.services.login.Login;
import com.svenruppert.expedition.planner.services.login.LoginService;

import static com.svenruppert.expedition.planner.services.login.LoginRepository.ANONYMOUS_LOGIN;
import static com.svenruppert.expedition.planner.services.user.UserRepository.ANONYMOUS_USER;

public class UserService implements HasLogger {

  private final UserRepository userRepository;
  private final LoginService loginService;

  public UserService(UserRepository userRepository,
                     LoginService loginService) {
    this.userRepository = userRepository;
    this.loginService = loginService;
  }

  public void saveRepository() {
    loginService.saveRepository();
    userRepository.saveRepository();
  }

  public DeleteEntityResponse<User> deleteUser(User user) {
    User userToDelete = userRepository.userByUID(user.uid());
    if (userToDelete.equals(ANONYMOUS_USER)) {
      return new DeleteEntityResponse<>(false, "User ANONYMOUS can not be deleted", ANONYMOUS_USER);
    }
    if (!user.equals(userToDelete)) {
      return new DeleteEntityResponse<>(false, "User are not equal for UID: " + user.uid(), ANONYMOUS_USER);
    }
    Login loginToDelete = loginService.userLoginByUID(user.uid());
    if (loginToDelete.equals(ANONYMOUS_LOGIN)) {
      return new DeleteEntityResponse<>(false, "User ANONYMOUS can not be deleted", ANONYMOUS_USER);
    }
    DeleteEntityResponse<Login> loginDeleteEntityResponse = loginService.deleteLogin(user.uid());
    if (!loginDeleteEntityResponse.deleted())
      return new DeleteEntityResponse<>(false,
          loginDeleteEntityResponse.message(),
          ANONYMOUS_USER);

    DeleteEntityResponse<User> deleteEntityResponse = userRepository.deleteUser(userToDelete);

    if (!deleteEntityResponse.deleted()) {
      if (loginService.reCreateLogin(loginToDelete).created()) {
        return new DeleteEntityResponse<>(false, "User " + user.uid() + " was not deleted", ANONYMOUS_USER);
      } else {
        logger().error("Login {} deletion could not rolled back", loginToDelete.uid());
        logger().error("Login TO RE_CREATE {}", loginToDelete);
        throw new RuntimeException("Login " + loginToDelete.uid() + " deletion could not rolled back");
      }
    }

    return deleteEntityResponse;
  }

  public User userByUserName(String username) {
    Login login = loginService.userLoginByLoginName(username);
    return userByUID(login.uid());
  }

  public User userByUID(int uid) {
    return userRepository.userByUID(uid);
  }

  public User userByLogin(Login login) {
    return userRepository.userByUID(login.uid());
  }


  public CreateEntityResponse<User> createUser(String loginName,
                                               String password,
                                               String forename,
                                               String surname) {
    logger().info("try to create user {}", loginName);
    CreateEntityResponse<Login> isLoginCreated = loginService.createLogin(loginName, password);
    if (!isLoginCreated.created()) {
      return new CreateEntityResponse<>(false, isLoginCreated.message(), ANONYMOUS_USER);
    } else {
      logger().warn("Login is created.. start creating User {}", loginName);
      Login login = loginService.userLoginByLoginName(loginName);
      return userRepository.createUser(login.uid(), forename, surname);
    }
  }

  public UpdateEntityResponse<Login> updatePassword(int uid, String passwordOld, String passwordNew) {
    return loginService.changePassword(uid, passwordOld, passwordNew);
  }

}
