package com.svenruppert.expedition.planner.views;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard")
public class DashboardView extends AbstractView<VerticalLayout> {

  public static final String MENU_ITEM_DASHBOARD = "mainlayout.menuitem.dashboard";

  public DashboardView() {
    super(MENU_ITEM_DASHBOARD);
    getContent().add(new H1("Dashboard")); //TODO i18n
  }
}
