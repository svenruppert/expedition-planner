package com.svenruppert.expedition.planner.views.orders;

import com.svenruppert.expedition.planner.MainLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@Route(value = "allOrders", layout = MainLayout.class)
@PreserveOnRefresh
public class AllOrdersView extends AbstractOrdersView {

  public static final String SUB_TITLE = "orders.all.subtitle";

  public AllOrdersView() {
    super(SUB_TITLE);
    getContent().add(new TextField("TBD ALL Orders"));
  }

  @Override
  public void localeChange(LocaleChangeEvent event) {

  }
}
