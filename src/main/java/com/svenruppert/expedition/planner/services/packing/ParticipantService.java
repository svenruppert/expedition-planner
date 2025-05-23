package com.svenruppert.expedition.planner.services.packing;

import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.services.persistence.AbstractService;

public class ParticipantService
    extends AbstractService<Participant> {

  public ParticipantService(ParticipantRepository participantRepository) {
    super(participantRepository);
  }
}
