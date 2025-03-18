package com.svenruppert.expedition.planner.services.user;

public record User(int uid, String forename, String surname) {
  public User() {
    this(-1, "anonymous", "anonymous");
  }
}
