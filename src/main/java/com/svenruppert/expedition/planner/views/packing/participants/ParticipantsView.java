package com.svenruppert.expedition.planner.views.packing.participants;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.components.CrudGrid;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(ParticipantsView.VIEW_NAME)
public class ParticipantsView extends AbstractView<VerticalLayout> {

    public static final String SUB_TITLE = "packing.participants.subtitle";
    public static final String VIEW_NAME = PackingMainLayout.PACKING_ROUTE + "participants";
    private final CrudGrid<Participant> participantsGrid;

    public static List<Participant> participantList = new ArrayList<>(
            List.of(new Participant("Alice"), new Participant("Bob"), new Participant("Carl"))
    );

    public ParticipantsView() {
        super(SUB_TITLE);

        participantsGrid = new CrudGrid<>(Participant.class, new ParticipantsDialog(this::saveItem, this::deleteItem));
        participantsGrid.getGrid().setItems(participantList);

        getContent().add(participantsGrid);
        getContent().setSizeFull();
    }

    private void saveItem(Participant participant) {
        if (!participantList.contains(participant)) {
            participantList.add(participant);
        }
        participantsGrid.getGrid().getDataProvider().refreshAll();
    }

    private void deleteItem(Participant participant) {
        participantList.remove(participant);
        participantsGrid.getGrid().getDataProvider().refreshAll();
    }
}
