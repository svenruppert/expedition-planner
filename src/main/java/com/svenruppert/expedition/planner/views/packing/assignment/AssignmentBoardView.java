package com.svenruppert.expedition.planner.views.packing.assignment;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.data.entity.Item;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.services.SingletonRegistry;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.svenruppert.expedition.planner.views.packing.itemlist.ItemListView;
import com.svenruppert.expedition.planner.views.packing.participants.ParticipantsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(AssignmentBoardView.VIEW_ROUTE)
public class AssignmentBoardView extends AbstractView<VerticalLayout> {

  public static final String SUB_TITLE = "packing.assignment.subtitle";
  public static final String VIEW_ROUTE = PackingMainLayout.PACKING_ROUTE + "assignmentboard";

  private ParticipantsService participantsService = SingletonRegistry.getOrCreateParticipantsService();

  public AssignmentBoardView() {
    super(SUB_TITLE);

    var itemList = ItemListView.itemList;
    var participantList = participantsService.allParticipants();

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
    return (item.isShared() && !participant.getName().equals("Alice")) ? new Div() : new Checkbox();
  }
}
