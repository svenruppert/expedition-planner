package junit.com.svenruppert.expedition.planner.services.login.passwords.checks.tests;


import com.svenruppert.expedition.planner.services.login.passwords.checks.PasswordValidator;
import com.svenruppert.expedition.planner.services.login.passwords.checks.DigitValidator;
import junit.com.svenruppert.expedition.planner.services.login.passwords.checks.AbstractPasswordValidatorTest;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class DigitValidatorTest
    extends AbstractPasswordValidatorTest {

  @Override
  protected Stream<InputPair> generateInputPairs() {
    return Stream.of(
        new InputPair("hello3World", true),
        new InputPair("helloWorld", false)
    );
  }

  @Override
  protected Supplier<PasswordValidator> validatorSupplier() {
    return DigitValidator::new;
  }
}
