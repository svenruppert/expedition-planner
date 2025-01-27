package com.svenruppert.expedition.planner.views.orders;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route(value = OrdersMainLayout.ORDERS_ROUTE + "completed")
public class CompletedOrdersView extends AbstractView<VerticalLayout> {

  public static final String SUB_TITLE = "orders.completed.subtitle";

  public CompletedOrdersView() {
    super(SUB_TITLE);
    getContent().add(new TextField("TBD Completed"));
  }
}
