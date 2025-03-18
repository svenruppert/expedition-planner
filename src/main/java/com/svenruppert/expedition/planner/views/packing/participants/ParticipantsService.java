package com.svenruppert.expedition.planner.views.packing.participants;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.data.entity.Participant;

import java.util.Collection;

public class ParticipantsService
    implements HasLogger {

  private ParticipantRepository participantRepository;

  public ParticipantsService(ParticipantRepository participantRepository) {
    this.participantRepository = participantRepository;
  }


  public void addParticipant(Participant participant) {
    logger().info("Adding participant " + participant.getName());
    participantRepository.addParticipant(participant);
  }

  public void createParticipant(String name) {
    logger().info("Creating participant " + name);
    Participant participant = new Participant(name);
    participantRepository.addParticipant(participant);
  }

  public Participant getParticipant(String name) {
    logger().info("Retrieving participant " + name);
    return participantRepository.getParticipantByName(name);
  }

  public void deleteParticipant(Participant participant) {
    logger().info("Deleting participant " + participant);
    participantRepository.deleteParticipant(participant);
  }

  public Collection<Participant> allParticipants() {
    logger().info("allParticipants...");
    return participantRepository.getAllParticipants();
  }


  public void saveRepository() {
    participantRepository.saveRepository();
  }
}
