package com.svenruppert.expedition.planner.services.login;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.DeleteEntityResponse;
import com.svenruppert.expedition.planner.services.UpdateEntityResponse;

import java.util.Map;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreatePersistenceService;
import static java.util.HashMap.newHashMap;

public class LoginRepository
    implements HasLogger {

  public static final Login ANONYMOUS_LOGIN = new Login();
  private static final GlobalUIDGenerator GLOBAL_UID_GENERATOR = GlobalUIDGenerator.getInstance();
  private final Map<String, Login> userLoginRepoLoginName = newHashMap(100);
  private final Map<Integer, Login> userLoginRepoUid = newHashMap(100);

  public void saveRepository() {
    getOrCreatePersistenceService().store(userLoginRepoLoginName);
    getOrCreatePersistenceService().store(userLoginRepoUid);
  }

  public Login userLoginByUID(int uid) {
    return userLoginRepoUid.getOrDefault(uid, ANONYMOUS_LOGIN);
  }

  public Login userLoginByLoginName(String loginName) {
    return userLoginRepoLoginName.getOrDefault(loginName, ANONYMOUS_LOGIN);
  }

  public CreateEntityResponse<Login> createLogin(Login login) {
    return createLogin(login.loginName(), login.passwordHash(), login.salt());
  }

  public CreateEntityResponse<Login> createLogin(String loginName,
                                                 String passwordHash,
                                                 String salt) {
    logger().info("creating login {}", loginName);
    if (userLoginRepoLoginName.containsKey(loginName)) {
      return new CreateEntityResponse<>(false, "Login " + loginName + " already exists", ANONYMOUS_LOGIN);
    }
    int nextUID = GLOBAL_UID_GENERATOR.getNextUID();
    try {
      Login value = new Login(loginName, passwordHash, salt, nextUID);
      userLoginRepoUid.put(nextUID, value);
      userLoginRepoLoginName.put(value.loginName(), value);
      return new CreateEntityResponse<>(true, "Login " + loginName + " created", value);
    } catch (Exception e) {
      userLoginRepoUid.remove(nextUID);
      userLoginRepoLoginName.remove(loginName);
      return new CreateEntityResponse<>(false, e.getMessage(), ANONYMOUS_LOGIN);
    }
  }

  public UpdateEntityResponse<Login> changePassword(int uid, String passwordHash, String salt) {
    if (userLoginRepoUid.containsKey(uid)) {
      Login login = userLoginRepoUid.get(uid);
      Login newEntry = new Login(login.loginName(), passwordHash, salt, login.uid());
      userLoginRepoUid.replace(uid, newEntry);
      userLoginRepoLoginName.replace(login.loginName(), newEntry);
      return new UpdateEntityResponse<>(true, "Login " + login.loginName() + " updated", newEntry);
    } else {
      logger().warn("trying to update not know user with UID {}", uid);
      return new UpdateEntityResponse<>(false, "trying to update not know user with UID " + uid, ANONYMOUS_LOGIN);
    }
  }

  public UpdateEntityResponse<Login> updateLogin(Login login) {
    logger().info("updating login {}", login);
    if (userLoginRepoLoginName.containsKey(login.loginName()) &&
        userLoginRepoUid.containsKey(login.uid())) {
      userLoginRepoLoginName.put(login.loginName(), login);
      userLoginRepoUid.put(login.uid(), login);
      return new UpdateEntityResponse<>(true, "Login " + login.uid() + " updated", login);
    } else {
      return new UpdateEntityResponse<>(false, "Login " + login.uid() + " not found / not updated", ANONYMOUS_LOGIN);
    }
  }

  public CreateEntityResponse<Login> storeLogin(Login login) {
    logger().info("storing login {}", login);
    userLoginRepoLoginName.put(login.loginName(), login);
    userLoginRepoUid.put(login.uid(), login);
    return new CreateEntityResponse<>(true, "Login " + login.loginName() + " stored", login);
  }

  public DeleteEntityResponse<Login> deleteLogin(Login  login) {
    logger().info("deleting login {}", login.uid());
    var removedA = userLoginRepoUid.remove(login.uid());
    if (removedA == null) {
      logger().warn("could not delete login {} - was not in Map userLoginRepoUid", login.uid());
      return new DeleteEntityResponse<>(false, "Login " + login.uid() + " was not in Map userLoginRepoUid", login);
    }
    var removedB = userLoginRepoLoginName.remove(login.loginName());
    if (removedB == null) {
      logger().warn("could not delete login {} - was not in Map userLoginRepoLoginName", login.uid());
       return new DeleteEntityResponse<>(false, "Login " + login.uid() + " was not in Map userLoginRepoLoginName", login);
    }

    var equalsA = removedA.equals(login);
    var equalsB = removedB.equals(login);
    if(equalsA && equalsB) {
      logger().info("login {} has been deleted - persisting the repo now", login);
      saveRepository();
    } else {
      logger().warn("Data in Maps inconsistent.. A:{} B:{}", equalsA, equalsB);
    }
    return new DeleteEntityResponse<>(true, "Login " + login.uid() + " deleted", login);
  }

  public DeleteEntityResponse<Login> resetRepository() {
    logger().info("resetting Login repository");
    userLoginRepoUid.clear();
    userLoginRepoLoginName.clear();
    saveRepository();
    return new DeleteEntityResponse<>(true, "All logins deleted", ANONYMOUS_LOGIN);
  }


}
