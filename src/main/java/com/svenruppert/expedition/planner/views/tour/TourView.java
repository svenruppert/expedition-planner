package com.svenruppert.expedition.planner.views.tour;

import com.svenruppert.expedition.planner.data.entity.Tour;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

import java.util.Collection;
import java.util.Set;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreateParticipantsService;
import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreateTourService;

@Route("tours")
public class TourView extends VerticalLayout implements HasDynamicTitle {

    public static final String MENU_ITEM_TOUR = "mainlayout.menuitem.tour";
    public static final String TITLE = "tour.title";
    private final TourForm tourForm;

    private final Grid<Tour> grid = new Grid<>(Tour.class);

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
        var participantService = getOrCreateParticipantsService();

        var filterField = new TextField("");
        filterField.setPlaceholder("filter for tour name");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.EAGER);
        filterField.setWidthFull();
        filterField.addValueChangeListener(event ->
                grid.setItems(tourService.getByName(event.getValue())));

        Collection<Tour> tourList = tourService.all();
        grid.setItems(tourList);
        grid.setColumns("name");
        grid.addColumn(createParticipantsRenderer()).setHeader(getTranslation("tour.participants.header"));
        grid.setSizeFull();

        tourForm = new TourForm(tourService, participantService);
        tourForm.setTour(createEmtpyTourObject());
        tourForm.setTourSaveConsumer(_ -> {
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
        tourForm.setWidth(null);

        grid.asSingleSelect().addValueChangeListener(event ->
                tourForm.setTour(event.getValue() != null ? event.getValue() : createEmtpyTourObject()));

        var gridFormLayout = new HorizontalLayout(new VerticalLayout(filterField, grid), tourForm);
        gridFormLayout.setSizeFull();
        add(gridFormLayout);

        setSizeFull();
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
