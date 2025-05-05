package com.svenruppert.expedition.planner.views.tour;

import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.data.entity.Tour;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

import java.util.stream.Collectors;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreateTourService;

@Route("tours")
public class TourView extends VerticalLayout implements HasDynamicTitle {

    /***
     * 1. - create the class
     *    - configure routing
     *    - extend from component class
     *    - add link to menu in MainLayout
     * 2. - set up dynmic title
     * 3. - add grid and fetch data from backend
     */

    public static final String MENU_ITEM_TOUR = "mainlayout.menuitem.tour";
    public static final String TITLE = "tour.title";

    public TourView() {
        var tourService = getOrCreateTourService();

        var grid = new Grid<>(Tour.class);
        grid.setColumns("name");
        grid.addComponentColumn(tour -> {
                var partipantsList = tour.getParticipantSet().stream()
                        .map(Participant::getName)
                        .collect(Collectors.joining(", "));
                return new Span(partipantsList);
            })
                .setHeader(getTranslation("tour.participants"))
                .setSortable(true);
        grid.setItems(tourService.all());
        add(grid);
    }

    @Override
    public String getPageTitle() {
        return getTranslation(TITLE);
    }
}
