package junit.com.svenruppert.expedition.planner.services.login.passwords.checks;

import com.svenruppert.expedition.planner.services.login.passwords.PasswordCheckResult;
import com.svenruppert.expedition.planner.services.login.passwords.checks.PasswordValidator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayNameGeneration(PasswordValidatorDisplayNameGenerator.class)
public abstract class AbstractPasswordValidatorTest {

  @ParameterizedTest(name = "Test {index}: Input = {1} - Expected = {2}")
  @MethodSource("validatorTestData")
  public void passwordValidatorTest(
      Supplier<PasswordValidator> validatorSupplier,
      String input,
      boolean expectedResult){

    PasswordValidator validator = validatorSupplier.get();
    PasswordCheckResult checkResult = validator.isValid(input);
    assertNotNull(checkResult);
    assertEquals(expectedResult, checkResult.ok());
  }

  protected Stream<Arguments> validatorTestData(){
    return generateInputPairs()
        .map(pair -> Arguments.of(
            validatorSupplier(),
            pair.input, pair.expected));
  }

  protected abstract Stream<InputPair> generateInputPairs();
  protected abstract Supplier<PasswordValidator> validatorSupplier();
  public record InputPair(String input, boolean expected){}
}
