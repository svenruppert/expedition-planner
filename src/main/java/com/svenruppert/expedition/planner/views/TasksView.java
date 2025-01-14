package com.svenruppert.expedition.planner.views;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "tasks")
public class TasksView extends AbstractView<HorizontalLayout> {

    public static final String MENU_ITEM_TASKS = "mainlayout.menuitem.tasks";

    public TasksView() {
        super(MENU_ITEM_TASKS);
    }
}