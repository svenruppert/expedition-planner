package com.svenruppert.expedition.planner.views.packing.taskboard;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(TaskBoardView.VIEW_ROUTE)
public class TaskBoardView extends AbstractView<VerticalLayout> {

    public static final String SUB_TITLE = "packing.taskboard.subtitle";
    public static final String VIEW_ROUTE = PackingMainLayout.PACKING_ROUTE + "taskboard";

    public TaskBoardView() {
        super(SUB_TITLE);
    }
}
