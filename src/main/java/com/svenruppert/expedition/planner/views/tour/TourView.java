package com.svenruppert.expedition.planner.views.tour;

import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.SingletonRegistry;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

import java.util.Set;

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
     * 5. - full implementation of CRUD operations
     */

    public static final String MENU_ITEM_TOUR = "mainlayout.menuitem.tour";
    public static final String TITLE = "tour.title";

    private final TourForm tourForm = new TourForm(SingletonRegistry.getOrCreateTourService(),
            SingletonRegistry.getOrCreateParticipantsService());

    public TourView() {
        var tourService = getOrCreateTourService();

        var grid = new Grid<>(Tour.class);
        grid.setColumns("name");
        grid.addColumn(createParticipantsRenderer())
                .setHeader(getTranslation("tour.participants.header"))
                .setSortable(true);
        grid.setItems(tourService.all());
        grid.asSingleSelect().addValueChangeListener(event -> {
            tourForm.setTour(event.getValue() != null ? event.getValue() : createEmtpyTourObject());
        });
        grid.setWidthFull();

        tourForm.setTour(createEmtpyTourObject());
        tourForm.setTourSaveConsumer(tour -> {
            grid.getDataProvider().refreshAll();
            Notification
                    .show(getTranslation("tour.crud.save.message"), 3000, Notification.Position.BOTTOM_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        tourForm.setTourDeleteConsumer(_ -> {
            grid.getDataProvider().refreshAll();
            tourForm.setTour(createEmtpyTourObject());
            Notification
                    .show(getTranslation("tour.crud.delete.message"), 3000, Notification.Position.BOTTOM_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
        tourForm.setSizeUndefined();

        add(grid, tourForm);
    }

    private Tour createEmtpyTourObject() {
        return new Tour("", Set.of());
    }

    private LitRenderer<Tour> createParticipantsRenderer() {
        return LitRenderer.<Tour>of("""
                        <ol>
                            ${item.participants.map((participant) => html`<li>${participant.name}</li>`)}
                        </ol>
                    """)
                .withProperty("participants", Tour::getParticipantSet);
    }

    @Override
    public String getPageTitle() {
        return getTranslation(TITLE);
    }
}
