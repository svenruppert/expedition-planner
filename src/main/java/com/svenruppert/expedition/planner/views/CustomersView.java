package com.svenruppert.expedition.planner.views;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "customers")
public class CustomersView extends AbstractView<HorizontalLayout> {

    public static final String MENU_ITEM_CUSTOMERS = "mainlayout.menuitem.customers";

    public CustomersView() {
        super(MENU_ITEM_CUSTOMERS);
    }
}

