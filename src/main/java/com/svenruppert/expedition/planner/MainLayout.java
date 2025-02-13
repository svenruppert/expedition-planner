package com.svenruppert.expedition.planner;

import com.svenruppert.expedition.planner.views.*;
import com.svenruppert.expedition.planner.views.orders.AllOrdersView;
import com.svenruppert.expedition.planner.views.orders.OrdersMainLayout;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.svenruppert.expedition.planner.views.packing.checklist.CheckListView;
import com.svenruppert.expedition.planner.views.users.UsersView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

@Layout
@PreserveOnRefresh
public class MainLayout extends AppLayout implements LocaleChangeObserver, AfterNavigationObserver {

    public static final String APPLICATION_TITLE = "application.title";


    private H1 viewTitle = new H1();

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1(getTranslation(APPLICATION_TITLE));

        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = primaryNavigation();

        addToDrawer(header, scroller, createFooter());
    }

    private Scroller primaryNavigation() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem(
                        getTranslation(DashboardView.MENU_ITEM_DASHBOARD),
                        DashboardView.class,
                        DASHBOARD.create()),
                new SideNavItem(
                        getTranslation(CustomersView.MENU_ITEM_CUSTOMERS),
                        CustomersView.class,
                        USER_HEART.create()),
                new SideNavItem(
                        getTranslation(ProductsView.MENU_ITEM_PRODUCTS),
                        ProductsView.class,
                        PACKAGE.create()),
                new SideNavItem(
                        getTranslation(DocumentsView.MENU_ITEM_DOCUMENTS),
                        DocumentsView.class,
                        RECORDS.create()),
                new SideNavItem(
                        getTranslation(TasksView.MENU_ITEM_TASKS),
                        TasksView.class,
                        LIST.create()),
                new SideNavItem(
                        getTranslation(AnalyticsView.MENU_ITEM_ANALYTICS),
                        AnalyticsView.class,
                        CHART.create()),
                new SideNavItem(
                        getTranslation(UsersView.MENU_ITEM_USERS),
                        UsersView.class,
                        USERS.create()));

        //Create Menu Item for ORDERS and its sub views
        SideNavItem ordersSideNavItem = new SideNavItem(getTranslation(OrdersMainLayout.MENU_ITEM_ORDERS), AllOrdersView.class, VaadinIcon.CART.create());
        Set<String> orderRoutesSet = getPathAliasesForRoute(OrdersMainLayout.ORDERS_ROUTE);
        ordersSideNavItem.setPathAliases(orderRoutesSet);
        sideNav.addItemAtIndex(1, ordersSideNavItem);

        //Create Menu Item for PACKING ITEM MANAGEMENT and its sub views
        SideNavItem packingSideNavItem = new SideNavItem(getTranslation(PackingMainLayout.MENU_ITEMS_PACKING), CheckListView.class, SUITCASE.create());
        Set<String> packeingRoutesSet = getPathAliasesForRoute(PackingMainLayout.PACKING_ROUTE);
        packingSideNavItem.setPathAliases(packeingRoutesSet);
        sideNav.addItem(packingSideNavItem);

        Scroller scroller = new Scroller(sideNav);
        scroller.setClassName(LumoUtility.Padding.SMALL);
        return scroller;
    }

    private static @NotNull Set<String> getPathAliasesForRoute(String parentRoute) {
        return RouteConfiguration.forApplicationScope().getAvailableRoutes().stream()
                .map(RouteBaseData::getTemplate)
                .filter(template -> template.startsWith(parentRoute))
                .collect(Collectors.toSet());
    }

    private Footer createFooter() {
        return new Footer();
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        // i18N for the main Menu Entries
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String subTitle =
                UI.getCurrent().getCurrentView() instanceof HasDynamicTitle hasDynamicTitle ?
                        hasDynamicTitle.getPageTitle() :
                        "";
        viewTitle.setText(subTitle);
    }
}