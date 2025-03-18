package com.svenruppert.expedition.planner.views.packing.participants;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.components.CrudGrid;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreateParticipantsService;

@Route(ParticipantsView.VIEW_NAME)
public class ParticipantsView extends AbstractView<VerticalLayout> {

  public static final String SUB_TITLE = "packing.participants.subtitle";
  public static final String VIEW_NAME = PackingMainLayout.PACKING_ROUTE + "participants";
  private final CrudGrid<Participant> participantsGrid;
  public List<Participant> allParticipants = new ArrayList<>(); //TODO ??? public
  private ParticipantsService participantsService = getOrCreateParticipantsService();


  public ParticipantsView() {
    super(SUB_TITLE);
    allParticipants.addAll(participantsService.allParticipants());
    participantsGrid = new CrudGrid<>(Participant.class, new ParticipantsDialog(this::saveItem, this::deleteItem));
    participantsGrid.getGrid().setItems(allParticipants);
    getContent().add(participantsGrid);
    getContent().setSizeFull();
  }

  private void saveItem(Participant participant) {
    if (!allParticipants.contains(participant)) {
      participantsService.addParticipant(participant);
      participantsService.saveRepository();
      allParticipants.clear();
      allParticipants.addAll(participantsService.allParticipants());
    }
    participantsGrid.getGrid().getDataProvider().refreshAll();
  }

  private void deleteItem(Participant participant) {
    participantsService.deleteParticipant(participant);
    participantsService.saveRepository();
    allParticipants.clear();
    allParticipants.addAll(participantsService.allParticipants());
    participantsGrid.getGrid().getDataProvider().refreshAll();
  }
}
