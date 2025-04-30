package com.svenruppert.expedition.planner.views.packing.assignment;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.data.entity.Item;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.services.SingletonRegistry;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.svenruppert.expedition.planner.services.packing.PackingItemService;
import com.svenruppert.expedition.planner.services.packing.ParticipantService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Objects;

@Route(AssignmentBoardView.VIEW_ROUTE)
public class AssignmentBoardView extends AbstractView<VerticalLayout> {

    public static final String SUB_TITLE = "packing.assignment.subtitle";
    public static final String VIEW_ROUTE = PackingMainLayout.PACKING_ROUTE + "assignmentboard";

    public AssignmentBoardView() {
        super(SUB_TITLE);

        PackingItemService packingItemService = SingletonRegistry.getOrCreateItemService();
        var itemList = packingItemService.all();

        ParticipantService participantService = SingletonRegistry.getOrCreateParticipantsService();
        var participantList = participantService.all();

        var grid = new Grid<>(Item.class);
        grid.setColumns("name");

        participantList.forEach(participant ->
                grid.addComponentColumn(item -> createCheckbox(item, participant))
                        .setHeader(participant.getName()));

        grid.setItems(itemList);

        getContent().add(grid);
        getContent().setSizeFull();
    }

    private Component createCheckbox(Item item, Participant participant) {
        boolean isSharedAndParticipant = (item.isShared() && !Objects.equals(participant, item.getResponsibleForShared())) ;
        var checkbox = new Checkbox();
        checkbox.setEnabled(!isSharedAndParticipant);
        return checkbox;
    }
}
