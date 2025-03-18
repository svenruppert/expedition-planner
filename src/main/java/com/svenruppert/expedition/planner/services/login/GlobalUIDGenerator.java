package com.svenruppert.expedition.planner.services.login;

import java.util.concurrent.atomic.AtomicInteger;

public class GlobalUIDGenerator {

  private static final GlobalUIDGenerator INSTANCE = new GlobalUIDGenerator();

  private  final AtomicInteger counter = new AtomicInteger(0);

  private GlobalUIDGenerator() {
  }

public static GlobalUIDGenerator getInstance() {
    return INSTANCE;
}

  /**
   * Generates a unique integer UID in a thread-safe manner.
   *
   * @return the next unique UID
   * @throws RuntimeException if the UID exceeds the maximum value
   */
  public int getNextUID() {
    int nextID = counter.getAndIncrement();
    if (nextID < 0) {
      throw new RuntimeException("UID overflow. All possible UIDs are used.");
    }
    return nextID;
  }
}
