package com.svenruppert.expedition.planner.views.orders;

import com.svenruppert.expedition.planner.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;

@ParentLayout(MainLayout.class)
@Layout("/orders")
public class OrdersMainLayout
    extends Composite<VerticalLayout>
    implements RouterLayout, HasDynamicTitle {

  public static final String TITLE = "orders.title";

  public static final String MENU_ITEM_ORDERS = "mainlayout.menuitem.orders";

  public static final String CANCELLED = "orders.subnavigation.cancelled";
  public static final String COMPLETED = "orders.subnavigation.completed";
  public static final String OPEN = "orders.subnavigation.open";
  public static final String ALL = "orders.subnavigation.all";

  public OrdersMainLayout() {
    //I18NProvider i18NProvider =
    Tabs tabs = new Tabs();
    tabs.add(new Tab(new RouterLink(getTranslation(ALL), AllOrdersView.class)));
    tabs.add(new Tab(new RouterLink(getTranslation(OPEN), OpenOrdersView.class)));
    tabs.add(new Tab(new RouterLink(getTranslation(COMPLETED), CompletedOrdersView.class)));
    tabs.add(new Tab(new RouterLink(getTranslation(CANCELLED), CancelledOrdersView.class)));

    getContent().add(tabs);
  }

  @Override
  public String getPageTitle() {
    return getTranslation(TITLE);
  }
}
