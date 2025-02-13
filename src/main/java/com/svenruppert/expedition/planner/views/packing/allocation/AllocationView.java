package com.svenruppert.expedition.planner.views.packing.allocation;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(AllocationView.VIEW_ROUTE)
public class AllocationView extends AbstractView<VerticalLayout> {

    public static final String SUB_TITLE = "packing.allocation.subtitle";
    public static final String VIEW_ROUTE = PackingMainLayout.PACKING_ROUTE + "allocation";

    public AllocationView() {
        super(SUB_TITLE);

        getContent().add(new H1("Hello World"));
    }
}
