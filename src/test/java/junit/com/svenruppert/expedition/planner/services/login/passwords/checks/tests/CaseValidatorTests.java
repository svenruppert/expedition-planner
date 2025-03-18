package junit.com.svenruppert.expedition.planner.services.login.passwords.checks.tests;


import com.svenruppert.expedition.planner.services.login.passwords.checks.PasswordValidator;
import com.svenruppert.expedition.planner.services.login.passwords.checks.CaseValidator;
import junit.com.svenruppert.expedition.planner.services.login.passwords.checks.AbstractPasswordValidatorTest;

import java.util.function.Supplier;
import java.util.stream.Stream;


public class CaseValidatorTests
    extends AbstractPasswordValidatorTest {

  @Override
  protected Stream<AbstractPasswordValidatorTest.InputPair> generateInputPairs() {
    return Stream.of(
        new InputPair("helloWorld", true),
        new InputPair("helloworld", false)
    );
  }

  @Override
  protected Supplier<PasswordValidator> validatorSupplier() {
    return CaseValidator::new;
  }
}
