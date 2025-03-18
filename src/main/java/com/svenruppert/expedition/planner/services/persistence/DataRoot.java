package com.svenruppert.expedition.planner.services.persistence;

import com.svenruppert.expedition.planner.services.login.LoginRepository;
import com.svenruppert.expedition.planner.services.user.UserRepository;
import com.svenruppert.expedition.planner.views.packing.participants.ParticipantRepository;


public class DataRoot {

  private final LoginRepository loginRepository = new LoginRepository();
  private final UserRepository userRepository = new UserRepository();
  private final ParticipantRepository participantRepository = new ParticipantRepository();
  public String helloWorld;

  public ParticipantRepository getParticipantRepository() {
    return participantRepository;
  }

  public LoginRepository getLoginRepository() {
    return loginRepository;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }


  @Override
  public String toString() {
    return "DataRoot{" +
        "loginRepository=" + loginRepository +
        ", userRepository=" + userRepository +
        ", participantRepository=" + participantRepository +
        ", helloWorld='" + helloWorld + '\'' +
        '}';
  }
}
