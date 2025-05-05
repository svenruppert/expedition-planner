package com.svenruppert.expedition.planner.views.tour;

import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.UpdateEntityResponse;
import com.svenruppert.expedition.planner.services.packing.ParticipantService;
import com.svenruppert.expedition.planner.services.tour.TourService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.SerializableConsumer;

import java.util.Optional;

public class TourForm extends VerticalLayout {

    private final TourService tourService;

    private final Binder<Tour> binder = new Binder<>(Tour.class);

    private SerializableConsumer<Tour> tourSaveConsumer ;

    public TourForm(TourService tourService, ParticipantService participantService) {
        this.tourService = tourService;
        var nameField = new TextField("Name");
        var participantsField = new MultiSelectComboBox<Participant>("Participants");
        participantsField.setItemLabelGenerator(Participant::getName);
        participantsField.setItems(participantService.all());

        add(nameField, participantsField);

        binder.forField(nameField)
                .withValidator(s -> !s.isBlank(), "Tour name is required")
                .bind(Tour::getName, Tour::setName);

        binder.forField(participantsField)
                .bind(Tour::getParticipantSet, Tour::setParticipantSet);

        var saveButton = new Button("Save", this::save);
        add(new HorizontalLayout(saveButton));
    }

    private void save(ClickEvent<Button> buttonClickEvent) {
        if (binder.isValid()) {
            UpdateEntityResponse<Tour> response = tourService.save(binder.getBean());

            if (response.updated())
                Optional.of(tourSaveConsumer)
                        .ifPresent(tourSerializableConsumer ->
                                tourSerializableConsumer.accept(response.value()));
        }
    }

    public void setTour(Tour tour) {
        binder.setBean(tour);
    }

    public void setTourSaveConsumer(SerializableConsumer<Tour> tourSaveConsumer) {
        this.tourSaveConsumer = tourSaveConsumer;
    }
}
