package com.svenruppert.expedition.planner.views.orders;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route(value = "/orders/cancelled")
public class CancelledOrdersView extends AbstractView<VerticalLayout> {

  public static final String SUB_TITLE = "orders.cancelled.subtitle";

  public CancelledOrdersView() {
    super(SUB_TITLE);
    getContent().add(new TextField("TBD cancelled"));
  }
}
