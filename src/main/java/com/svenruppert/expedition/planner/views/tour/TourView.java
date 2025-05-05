package com.svenruppert.expedition.planner.views.tour;

import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.SingletonRegistry;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

import java.util.stream.Collectors;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreateTourService;

@Route("tours")
public class TourView extends HorizontalLayout implements HasDynamicTitle {

    /***
     * 1. - create the class
     *    - configure routing
     *    - extend from component class
     *    - add link to menu in MainLayout
     * 2. - set up dynmic title
     * 3. - add grid and fetch data from backend
     * 4. - create a tour form and extract components to an extra component
     */

    public static final String MENU_ITEM_TOUR = "mainlayout.menuitem.tour";
    public static final String TITLE = "tour.title";

    private final TourForm tourForm = new TourForm(SingletonRegistry.getOrCreateTourService(),
            SingletonRegistry.getOrCreateParticipantsService());

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
                .setHeader(getTranslation("tour.participants.header"))
                .setSortable(true);
        grid.setItems(tourService.all());
        grid.asSingleSelect().addValueChangeListener(event -> {
            tourForm.setTour(event.getValue());
        });
        grid.setWidthFull();

        tourForm.setTourSaveConsumer(tour -> {
            grid.getDataProvider().refreshItem(tour);
        });
        tourForm.setSizeUndefined();

        add(grid, tourForm);
    }

    @Override
    public String getPageTitle() {
        return getTranslation(TITLE);
    }
}
