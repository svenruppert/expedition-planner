package junit.com.svenruppert.expedition.planner.services.login.passwords.checks;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class PasswordValidatorDisplayNameGenerator
    extends DisplayNameGenerator.Standard {


  private String simpleClassName(String className){
    return className.substring(className.lastIndexOf('.') + 1);
  }

  @Override
  public String generateDisplayNameForMethod(
      Class<?> testClass, Method testMethod) {
    Parameter[] parameters = testMethod.getParameters();
    ParameterizedType parameterizedType = (ParameterizedType) parameters[0].getParameterizedType();
    Type[] typeArguments = parameterizedType.getActualTypeArguments();
    String validatorName = typeArguments[0].getTypeName();
    String simpleClassName = simpleClassName(validatorName);

    Object input = simpleClassName(parameters[1].getType().getName());
    Object expectedResult = simpleClassName(parameters[2].getType().getName());
    return String.format("%s - Input: %s - Expected: %s", simpleClassName, input, expectedResult);
  }
}
