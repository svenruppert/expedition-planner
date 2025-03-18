package com.svenruppert.expedition.planner.services.login.passwords;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordHasher {
  //TODO Iterations and key length should be stored per login as well
  private static final int ITERATIONS = 65536;
  private static final int KEY_LENGTH = 256;
  //TODO switch to ARGO2 - needs dependency
  private static final String ALGORITHM = "PBKDF2WithHmacSHA256";


  public String hashPassword(String password, String salt) {
    try {
      final KeySpec spec = new PBEKeySpec(
          password.toCharArray(),
          salt.getBytes(),
          ITERATIONS,
          KEY_LENGTH);
      SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM); //not ThreadSafe
      byte[] hash = factory.generateSecret(spec).getEncoded();
      return Base64.getEncoder().encodeToString(hash);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("Error while hashing password", e);
    }
  }
}
