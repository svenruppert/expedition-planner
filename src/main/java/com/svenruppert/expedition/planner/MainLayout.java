package com.svenruppert.expedition.planner;

import com.svenruppert.expedition.planner.components.AbstractViewHeader;
import com.svenruppert.expedition.planner.views.*;
import com.svenruppert.expedition.planner.views.orders.AllOrdersView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.theme.lumo.LumoUtility;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class MainLayout extends AppLayout implements LocaleChangeObserver {

  public static final String APPLICATION_TITLE = "application.title";
  public static final String MENU_ITEM_DASHBOARD = "mainlayout.menuitem.dashboard";
  public static final String MENU_ITEM_ORDERS = "mainlayout.menuitem.orders";
  public static final String MENU_ITEM_CUSTOMERS = "mainlayout.menuitem.customers";
  public static final String MENU_ITEM_PRODUCTS = "mainlayout.menuitem.products";
  public static final String MENU_ITEM_DOCUMENTS = "mainlayout.menuitem.documents";
  public static final String MENU_ITEM_TASKS = "mainlayout.menuitem.tasks";
  public static final String MENU_ITEM_ANALYTICS = "mainlayout.menuitem.analytics";

  private H1 appTitle = new H1();

  public MainLayout() {
    customizeTitle();
    addToDrawer(appTitle, primaryNavigation());
    setPrimarySection(Section.DRAWER);
  }

  private void customizeTitle() {
//    appTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
//        .set("line-height", "var(--lumo-size-l)")
//        .set("margin", "0 var(--lumo-space-m)");
  }

  private Scroller primaryNavigation() {
    SideNav sideNav = new SideNav();
    sideNav.addItem(
        new SideNavItem(
            getTranslation(MENU_ITEM_DASHBOARD),
            DashboardView.class,
            DASHBOARD.create()),
        new SideNavItem(
            getTranslation(MENU_ITEM_ORDERS),
            AllOrdersView.class,
            CART.create()),
        new SideNavItem(
            getTranslation(MENU_ITEM_CUSTOMERS),
            CustomersView.class,
            USER_HEART.create()),
        new SideNavItem(
            getTranslation(MENU_ITEM_PRODUCTS),
            ProductsView.class,
            PACKAGE.create()),
        new SideNavItem(
            getTranslation(MENU_ITEM_DOCUMENTS),
            DocumentsView.class,
            RECORDS.create()),
        new SideNavItem(
            getTranslation(MENU_ITEM_TASKS),
            TasksView.class,
            LIST.create()),
        new SideNavItem(
            getTranslation(MENU_ITEM_ANALYTICS),
            AnalyticsView.class,
            CHART.create()));

    Scroller scroller = new Scroller(sideNav);
    scroller.setClassName(LumoUtility.Padding.SMALL);
    return scroller;
  }

  private AbstractViewHeader lastNavBar;

  public void updateSecondaryNavigation(AbstractViewHeader navBar) {
    if (lastNavBar != null) remove(lastNavBar);
    lastNavBar = navBar;
    addToNavbar(navBar);
  }

  @Override
  public void localeChange(LocaleChangeEvent event) {
    appTitle.setText(getTranslation(APPLICATION_TITLE));
    // i18N for the main Menu Entries
  }
}