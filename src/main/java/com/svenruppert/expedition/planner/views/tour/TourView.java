package com.svenruppert.expedition.planner.views.tour;

import com.svenruppert.expedition.planner.data.entity.Tour;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

import java.util.Collection;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreateTourService;

@Route("tours")
public class TourView extends VerticalLayout implements HasDynamicTitle {

    public static final String MENU_ITEM_TOUR = "mainlayout.menuitem.tour";
    public static final String TITLE = "tour.title";
    private final TourForm tourForm;
    /***
     * 1. create the class
     * 2. configure routing
     * 3. extend from component class
     * 4. add link to menu in MainLayout
     * 5. add dynamic title
     * 6. create a grid and instance of tour service and fetch data
     * 7. create a tour form and extract components to an extra component
     * 8. add databinding and refresh grid with consumer
     */

    public TourView() {
        var tourService = getOrCreateTourService();

        var grid = new Grid<>(Tour.class);
        Collection<Tour> tourList = tourService.all();
        grid.setItems(tourList);
        grid.setSizeFull();

        tourForm = new TourForm(tourService);
        tourForm.setTourSaveConsumer(tour -> grid.getDataProvider().refreshAll());
        tourForm.setWidth(null);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                tourForm.setTour(event.getValue());
            }
        });

        var gridFormLayout = new HorizontalLayout(grid, tourForm);
        gridFormLayout.setSizeFull();
        add(gridFormLayout);

        setSizeFull();
    }

    @Override
    public String getPageTitle() {
        return getTranslation(TITLE);
    }
}
