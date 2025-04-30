package com.svenruppert.expedition.planner.services.persistence;

import com.svenruppert.expedition.planner.services.login.LoginRepository;
import com.svenruppert.expedition.planner.services.packing.PackingItemRepository;
import com.svenruppert.expedition.planner.services.user.UserRepository;
import com.svenruppert.expedition.planner.services.packing.ParticipantRepository;
import com.svenruppert.expedition.planner.services.tour.TourRepository;


public class DataRoot {

  private final LoginRepository loginRepository = new LoginRepository();
  private final UserRepository userRepository = new UserRepository();
  private final ParticipantRepository participantRepository = new ParticipantRepository();
  private final PackingItemRepository packingItemRepository = new PackingItemRepository();
  private final TourRepository tourRepository = new TourRepository();
  //TODO - remove
  public String helloWorld;

  public ParticipantRepository getParticipantRepository() {
    return participantRepository;
  }

  public PackingItemRepository getItemRepository() {
    return packingItemRepository;
  }

  public LoginRepository getLoginRepository() {
    return loginRepository;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public TourRepository getTourRepository() {
    return tourRepository;
  }

  @Override
  public String toString() {
    return "DataRoot{" +
        "loginRepository=" + loginRepository +
        ", userRepository=" + userRepository +
        ", participantRepository=" + participantRepository +
        ", itemRepository=" + packingItemRepository +
        ", tourRepository=" + tourRepository +
        '}';
  }
}
