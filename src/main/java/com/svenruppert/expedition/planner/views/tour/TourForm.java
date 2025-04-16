package com.svenruppert.expedition.planner.views.tour;

import com.svenruppert.expedition.planner.data.entity.Tour;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.SerializableConsumer;

import java.util.Optional;

public class TourForm extends VerticalLayout {
    private final TourService tourService;

    private final Binder<Tour> tourBinder = new Binder<>(Tour.class);

    private Optional<SerializableConsumer<Tour>> tourSaveConsumerOptional = Optional.empty();

    public TourForm(TourService tourService) {
        this.tourService = tourService;

        var nameField = new TextField("Tour name");

        tourBinder.forField(nameField)
                .asRequired()
                .withValidator(s -> !s.isBlank(), "Tour name is required")
                .bind(Tour::getName, Tour::setName);

        var cancelButton = new Button("Cancel", this::cancel);
        var saveButton = new Button("Save", this::save);
        var buttonLayout = new HorizontalLayout(cancelButton, saveButton);
        var tourFormLayout = new VerticalLayout(nameField, buttonLayout);
        add(tourFormLayout);
    }

    public void setTourSaveConsumer(SerializableConsumer<Tour> tourSaveConsumer) {
        this.tourSaveConsumerOptional = Optional.of(tourSaveConsumer);
    }

    public void setTour(Tour tour) {
        tourBinder.setBean(tour);
    }

    private void save(ClickEvent<Button> buttonClickEvent) {
        tourService.add(tourBinder.getBean());
        this.tourSaveConsumerOptional.ifPresent(consumer -> consumer.accept(tourBinder.getBean()));
    }

    private void cancel(ClickEvent<Button> buttonClickEvent) {
        tourBinder.removeBean();
    }
}
