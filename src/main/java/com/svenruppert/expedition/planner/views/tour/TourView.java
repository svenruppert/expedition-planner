package com.svenruppert.expedition.planner.views.tour;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route("tours")
public class TourView extends VerticalLayout {

    /***
     * 1. - create the class
     *    - configure routing
     *    - extend from component class
     *    - add link to menu in MainLayout
     * 2. - set up dynmic title
     */

    public static final String MENU_ITEM_TOUR = "mainlayout.menuitem.tour";

    public TourView() {

    }
}
