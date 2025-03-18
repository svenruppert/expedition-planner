package com.svenruppert.expedition.planner.services.login.passwords;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SaltGenerator {
  public static final int DEFAULT_SALT_LENGTH = 16;

  private static final Logger LOGGER = LoggerFactory.getLogger(SaltGenerator.class);
  private static final String ALGORITHM = "DRBG";

  private static SecureRandom secureRandom;

  public static SecureRandom getSecureRandom() {
    if (secureRandom == null) {
      synchronized (SaltGenerator.class) {
        if (secureRandom == null) {
          try {
            secureRandom = SecureRandom.getInstance(ALGORITHM);
          } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            secureRandom = new SecureRandom();
          }
        }
      }
    }
    return secureRandom;
  }

  public String generateSalt(int length) {
    byte[] salt = new byte[length];
    getSecureRandom().nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }

  public String generateSalt() {
    return generateSalt(DEFAULT_SALT_LENGTH);
  }

}
