package junit.com.svenruppert.expedition.planner.services.user;

import com.svenruppert.expedition.planner.services.SingletonRegistry;
import com.svenruppert.expedition.planner.services.auth.AuthService;
import com.svenruppert.expedition.planner.services.login.LoginRepository;
import com.svenruppert.expedition.planner.services.login.LoginService;
import com.svenruppert.expedition.planner.services.login.passwords.SaltGenerator;
import com.svenruppert.expedition.planner.services.user.UserRepository;
import com.svenruppert.expedition.planner.services.user.UserService;
import org.junit.jupiter.api.extension.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.*;

public class ServicesSingletonsParameterResolver
    implements ParameterResolver {

//  private static volatile LoginRepository loginRepository;
//  private static volatile LoginService loginService;
//  private static volatile UserRepository userRepository;
//  private static volatile UserService userService;
//  private static volatile AuthService authService;
//
//  protected static AuthService getOrCreateAuthService() {
//    if (authService == null) {
//      synchronized (ServicesSingletonsParameterResolver.class) {
//        if (authService == null) {
//          authService = new AuthService(
//              getOrCreateLoginService(),
//              getOrCreateUserService());
//        }
//      }
//    }
//    return authService;
//  }
//
//  protected static UserService getOrCreateUserService() {
//    if (userService == null) {
//      synchronized (ServicesSingletonsParameterResolver.class) {
//        if (userService == null) {
//          userService = new UserService(
//              getOrCreateUserRepository(),
//              getOrCreateLoginService());
//        }
//      }
//    }
//    return userService;
//  }
//
//  protected static UserRepository getOrCreateUserRepository() {
//    if (userRepository == null) {
//      synchronized (ServicesSingletonsParameterResolver.class) {
//        if (userRepository == null) {
//          userRepository = new UserRepository();
//        }
//      }
//    }
//    return userRepository;
//  }
//
//  protected static LoginService getOrCreateLoginService() {
//    if (loginService == null) {
//      synchronized (ServicesSingletonsParameterResolver.class) {
//        if (loginService == null) {
//          loginService = new LoginService(getOrCreateLoginRepository(), new SaltGenerator());
//        }
//      }
//    }
//    return loginService;
//  }
//
//  protected static LoginRepository getOrCreateLoginRepository() {
//    if (loginRepository == null) {
//      synchronized (ServicesSingletonsParameterResolver.class) {
//        if (loginRepository == null) {
//          loginRepository = new LoginRepository();
//        }
//      }
//    }
//    return loginRepository;
//  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext)
      throws ParameterResolutionException {

    Class<?> type = parameterContext.getParameter().getType();
    return type == LoginRepository.class ||
        type == LoginService.class ||
        type == UserRepository.class ||
        type == UserService.class ||
        type == AuthService.class;
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext,
                                 ExtensionContext extensionContext)
      throws ParameterResolutionException {
    Class<?> type = parameterContext.getParameter().getType();
    if (type == LoginRepository.class) {
      return getOrCreateLoginRepository();
    }
    if (type == LoginService.class) {
      return getOrCreateLoginService();
    }
    if (type == UserRepository.class) {
      return getOrCreateUserRepository();
    }
    if (type == UserService.class) {
      return getOrCreateUserService();
    }
    if (type == AuthService.class) {
      return getOrCreateAuthService();
    }

    throw new ParameterResolutionException("Unsupported parameter: " + type);
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.PARAMETER, ElementType.TYPE})
  @ExtendWith(ServicesSingletonsParameterResolver.class)
  public @interface SingletonService {
  }
}
