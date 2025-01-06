package com.svenruppert.expedition.planner.views.orders;

import com.svenruppert.expedition.planner.MainLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@Route(value = "openOrders", layout = MainLayout.class)
@PreserveOnRefresh
public class OpenOrdersView extends AbstractOrdersView {
  public static final String SUB_TITLE = "orders.open.subtitle";

  public OpenOrdersView() {
    super(SUB_TITLE);
    getContent().add(new TextField("TBD Open"));
  }

  @Override
  public void localeChange(LocaleChangeEvent event) {

  }


}
