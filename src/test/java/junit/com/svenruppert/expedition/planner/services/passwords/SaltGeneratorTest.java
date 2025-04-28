package junit.com.svenruppert.expedition.planner.services.passwords;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.services.login.passwords.SaltGenerator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled
class SaltGeneratorTest
    implements HasLogger {

  @ParameterizedTest
  @ValueSource(ints= {1,2,3,4,5,6,7,8,9})
  @ValueSource(ints= {10,20,30,40,50,60,70,80,90})
  @ValueSource(ints= {100,200,300,400,500,600,700,800,900})
  void generateSalt(int saltLength) {
    SaltGenerator saltGenerator = new SaltGenerator();
    Set<String> saltSet = new HashSet<>();
    for(int i = 0; i < 10_000_000; i++) {
      String generatedSalt = saltGenerator.generateSalt(saltLength);
      boolean contains = saltSet.contains(generatedSalt);
      if (contains) {
        logger().info("Generated salt: {}", generatedSalt);
        logger().info("i = {}" , i);
        logger().info("slatLength = {}" , saltLength);
      }
      assertFalse(contains);
      saltSet.add(generatedSalt);
    }
  }
}