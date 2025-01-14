package com.svenruppert.expedition.planner.views;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "analytics")
public class AnalyticsView extends AbstractView<HorizontalLayout> {

    public static final String MENU_ITEM_ANALYTICS = "mainlayout.menuitem.analytics";

    public AnalyticsView() {
        super(MENU_ITEM_ANALYTICS);
    }
}
