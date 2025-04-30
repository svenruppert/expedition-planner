package com.svenruppert.expedition.planner.services.user;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.DeleteEntityResponse;

import java.util.Map;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreatePersistenceService;
import static java.util.HashMap.newHashMap;

public class UserRepository
    implements HasLogger {

  public static final User ANONYMOUS_USER = new User();
  private final Map<Integer, User> userRepo = newHashMap(100);

  public void saveRepository() {
    getOrCreatePersistenceService().store(userRepo);
  }


  public User userByUID(int uid) {
    return userRepo.getOrDefault(uid, ANONYMOUS_USER);
  }

  public DeleteEntityResponse<User> deleteUser(User user) {
    userRepo.remove(user.uid());
    return new DeleteEntityResponse<>(true, String.format("User %s deleted", user.uid()), user);
  }


  public CreateEntityResponse<User> createUser(int uid, String forename, String surname) {
    User value = new User(uid, forename, surname);
    userRepo.put(uid, value);
    return new CreateEntityResponse<>(true, "User " + uid + " created", value);
  }

  public DeleteEntityResponse<User> resetRepository() {
    logger().info("reset all users");
    userRepo.clear();
    saveRepository();
    return new DeleteEntityResponse<>(true, "All users reset", ANONYMOUS_USER);
  }
}
