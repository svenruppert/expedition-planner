package com.svenruppert.expedition.planner.views.packing;

import com.svenruppert.expedition.planner.MainLayout;
import com.svenruppert.expedition.planner.views.packing.allocation.AllocationView;
import com.svenruppert.expedition.planner.views.packing.checklist.CheckListView;
import com.svenruppert.expedition.planner.views.packing.taskboard.TaskBoardView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;

@ParentLayout(MainLayout.class)
@Layout(PackingMainLayout.PACKING_ROUTE)
public class PackingMainLayout extends Composite<VerticalLayout>
        implements RouterLayout, HasDynamicTitle {

    public static final String PACKING_ROUTE = "packing/";
    public static final String MENU_ITEMS_PACKING = "mainlayout.menuitem.packing";
    public static final String TITLE = "packing.title";

    public PackingMainLayout() {
        Tabs tabs = new Tabs();

        tabs.add(new Tab(new RouterLink(getTranslation(CheckListView.SUB_TITLE), CheckListView.class)));
        tabs.add(new Tab(new RouterLink(getTranslation(AllocationView.SUB_TITLE), AllocationView.class)));
        tabs.add(new Tab(new RouterLink(getTranslation(TaskBoardView.SUB_TITLE), TaskBoardView.class)));

        getContent().add(tabs);
    }

    @Override
    public String getPageTitle() {
        return getTranslation(TITLE);
    }
}
