package junit.com.svenruppert.expedition.planner.services.user;

import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.DeleteEntityResponse;
import com.svenruppert.expedition.planner.services.UpdateEntityResponse;
import com.svenruppert.expedition.planner.services.login.Login;
import com.svenruppert.expedition.planner.services.login.LoginService;
import com.svenruppert.expedition.planner.services.user.User;
import com.svenruppert.expedition.planner.services.user.UserService;
import org.junit.jupiter.api.Test;

import static com.svenruppert.expedition.planner.services.user.UserRepository.ANONYMOUS_USER;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

  private static void checkMaxMustermann(User user) {
    assertNotNull(user);
    assertNotEquals(-1, user.uid());
    assertEquals("Max", user.forename());
    assertEquals("Mustermann", user.surname());
  }

  @Test
  void test001(
      @ServicesSingletonsParameterResolver.SingletonService UserService service,
      @ServicesSingletonsParameterResolver.SingletonService LoginService loginService) {

    CreateEntityResponse<User> createResponse = service.createUser(
        "max.mustermann",
        "SecureMe",
        "Max",
        "Mustermann");

    assertNotNull(createResponse);
    User user = createResponse.entity();

    checkMaxMustermann(user);
    checkMaxMustermann(service.userByUserName("max.mustermann"));
    checkMaxMustermann(service.userByUID(user.uid()));

    UpdateEntityResponse<Login> loginUpdate = loginService.changePassword(
        user.uid(),
        "SecureMe",
        "password");
    assertNotNull(loginUpdate);
    assertTrue(loginUpdate.updated());

    DeleteEntityResponse<User> deleteResult = service.deleteUser(user);
    assertNotNull(deleteResult);
    assertTrue(deleteResult.deleted());

    User deletedUser = service.userByUserName("max.mustermann");
    assertNotNull(deletedUser);
    assertEquals(-1, deletedUser.uid());
    assertEquals(ANONYMOUS_USER, deletedUser);

  }


}
