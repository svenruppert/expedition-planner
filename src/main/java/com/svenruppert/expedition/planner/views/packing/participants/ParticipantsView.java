package com.svenruppert.expedition.planner.views.packing.participants;

import com.svenruppert.expedition.planner.components.AbstractCrudView;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreateParticipantsService;

@Route(ParticipantsView.VIEW_NAME)
public class ParticipantsView extends AbstractCrudView<Participant, ParticipantService, VerticalLayout> {

  public static final String SUB_TITLE = "packing.participants.subtitle";
  public static final String VIEW_NAME = PackingMainLayout.PACKING_ROUTE + "participants";

  public ParticipantsView() {
    super(Participant.class, SUB_TITLE);
  }

  @Override
  protected ParticipantService getService() {
    return getOrCreateParticipantsService();
  }

  @Override
  protected Component createDialogForm(Binder<Participant> binder) {
    TextField nameField = new TextField(getTranslation("packing.participants.nameField.label"));
    nameField.setWidthFull();
    binder.bind(nameField, Participant::getName, Participant::setName);

    VerticalLayout rootLayout = new VerticalLayout();
    rootLayout.add(nameField);

    return rootLayout;
  }
}
