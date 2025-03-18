package junit.com.svenruppert.expedition.planner.services.login.passwords.checks.tests;


import com.svenruppert.expedition.planner.services.login.passwords.checks.PasswordValidator;
import com.svenruppert.expedition.planner.services.login.passwords.checks.CommonPasswordValidator;
import junit.com.svenruppert.expedition.planner.services.login.passwords.checks.AbstractPasswordValidatorTest;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class CommonPasswordValidatorTest
    extends AbstractPasswordValidatorTest {
  @Override
  protected Stream<InputPair> generateInputPairs() {
    return Stream.of(
        new InputPair("password", false),
        new InputPair("12345678", false),
        new InputPair("Hsjhhftr%&", true)
    );
  }

  @Override
  protected Supplier<PasswordValidator> validatorSupplier() {
    return CommonPasswordValidator::new;
  }
}
