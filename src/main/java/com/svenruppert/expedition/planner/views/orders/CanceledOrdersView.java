package com.svenruppert.expedition.planner.views.orders;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route(value = OrdersMainLayout.ORDERS_ROUTE + CanceledOrdersView.VIEW_ROUTE)
public class CanceledOrdersView extends AbstractView<VerticalLayout> {

  public static final String SUB_TITLE = "orders.canceled.subtitle";
  public static final String VIEW_ROUTE = "canceled";

  public CanceledOrdersView() {
    super(SUB_TITLE);
    getContent().add(new TextField("TBD canceled"));
  }
}
