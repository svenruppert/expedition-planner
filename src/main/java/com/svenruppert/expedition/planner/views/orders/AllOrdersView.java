package com.svenruppert.expedition.planner.views.orders;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(OrdersMainLayout.ORDERS_ROUTE + AllOrdersView.VIEW_ROUTE)
public class AllOrdersView extends AbstractView<VerticalLayout> {

  public static final String SUB_TITLE = "orders.all.subtitle";
    public static final String VIEW_ROUTE = "all";

    public AllOrdersView() {
      super(SUB_TITLE);
      getContent().add(new TextField("TBD ALL Orders"));
  }
}
