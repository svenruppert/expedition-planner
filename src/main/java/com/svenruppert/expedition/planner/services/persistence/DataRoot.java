package com.svenruppert.expedition.planner.services.persistence;

import com.svenruppert.expedition.planner.services.login.LoginRepository;
import com.svenruppert.expedition.planner.services.user.UserRepository;
import com.svenruppert.expedition.planner.views.packing.itemlist.ItemRepository;
import com.svenruppert.expedition.planner.views.packing.participants.ParticipantRepository;
import com.svenruppert.expedition.planner.views.tour.TourRepository;


public class DataRoot {

  private final LoginRepository loginRepository = new LoginRepository();
  private final UserRepository userRepository = new UserRepository();
  private final ParticipantRepository participantRepository = new ParticipantRepository();
  private final ItemRepository itemRepository = new ItemRepository();
  private final TourRepository tourRepository = new TourRepository();
  public String helloWorld;

  public ParticipantRepository getParticipantRepository() {
    return participantRepository;
  }

  public ItemRepository getItemRepository() {
    return itemRepository;
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
        ", itemRepository=" + itemRepository +
        ", helloWorld='" + helloWorld + '\'' +
        '}';
  }
}
