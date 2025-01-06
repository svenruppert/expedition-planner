package junit.com.svenruppert.rest;

import com.svenruppert.rest.UpperCaseService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpperCaseServiceTest {

  @Test
  void test001() {
    String hello = new UpperCaseService().toUpperCase("hello");
    assertEquals("HELLO", hello);
  }

}
