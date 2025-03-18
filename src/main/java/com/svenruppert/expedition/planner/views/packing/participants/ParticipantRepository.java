package com.svenruppert.expedition.planner.views.packing.participants;

import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.services.SingletonRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParticipantRepository {

  private final Map<String, Participant> participantsByName = new HashMap<>();

  public void addParticipant(Participant participant) {
    participantsByName.put(participant.getName(), participant);
  }

  public Participant getParticipantByName(String name) {
    return participantsByName.get(name);
  }

  public Collection<Participant> getAllParticipants() {
    return Collections.unmodifiableCollection(participantsByName.values());
  }

  public void deleteParticipant(Participant participant) {
    participantsByName.remove(participant.getName());
  }


  public void saveRepository() {
    SingletonRegistry.getOrCreatePersistenceService().store(participantsByName);
  }
}
