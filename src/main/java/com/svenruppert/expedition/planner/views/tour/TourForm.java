package com.svenruppert.expedition.planner.views.tour;

import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.tour.TourService;
import com.svenruppert.expedition.planner.services.packing.ParticipantService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Optional;

public class TourForm extends VerticalLayout {
    private final TourService tourService;

    private final Binder<Tour> tourBinder = new Binder<>(Tour.class);

    private SerializableConsumer<Tour> tourSaveConsumer ;
    private SerializableConsumer<Tour> tourDeleteConsumer ;

    public TourForm(TourService tourService, ParticipantService participantService) {
        this.tourService = tourService;

        var nameField = new TextField(getTranslation("tour.name.field.label"));
        nameField.setWidthFull();

        var participantsField = new MultiSelectComboBox<Participant>(getTranslation("tour.participants.field.label"));
        participantsField.setItems(participantService.all());
        participantsField.setItemLabelGenerator(Participant::getName);
        participantsField.setWidthFull();

        tourBinder.forField(nameField)
                .asRequired()
                .withValidator(s -> !s.isBlank(), getTranslation("tour.name.validation.notblank"))
                .bind(Tour::getName, Tour::setName);

        tourBinder.forField(participantsField)
                .bind(Tour::getParticipantSet, Tour::setParticipantSet);

        var deleteIcon = VaadinIcon.TRASH.create();
        deleteIcon.addClassNames(LumoUtility.TextColor.ERROR);

        var deleteButton = new Button(deleteIcon, this::delete);
        deleteButton.setTooltipText(getTranslation("cruddialog.delete.label"));

        var cancelButton = new Button(VaadinIcon.CLOSE.create(), this::cancel);
        cancelButton.setTooltipText(getTranslation("cruddialog.cancel.label"));

        var saveButton = new Button(getTranslation("cruddialog.save.label"), this::save);
        saveButton.setTooltipText(getTranslation("cruddialog.save.label"));
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var buttonLayout = new HorizontalLayout(deleteButton, cancelButton, saveButton);

        var formContent = new VerticalLayout(nameField, participantsField, buttonLayout);
        add(formContent);
    }

    private void delete(ClickEvent<Button> buttonClickEvent) {
        tourService.delete(tourBinder.getBean());
        Optional.of(tourDeleteConsumer).ifPresent(consumer -> consumer.accept(tourBinder.getBean()));
    }

    public void setTourSaveConsumer(SerializableConsumer<Tour> tourSaveConsumer) {
        this.tourSaveConsumer = tourSaveConsumer;
    }

    public void setTour(Tour tour) {
        tourBinder.setBean(tour);
    }

    private void save(ClickEvent<Button> buttonClickEvent) {
        if (tourBinder.validate().isOk()) {
            tourService.createOrUpdate(tourBinder.getBean());
            Optional.of(this.tourSaveConsumer).ifPresent(consumer -> consumer.accept(tourBinder.getBean()));
        }
    }

    private void cancel(ClickEvent<Button> buttonClickEvent) {
        tourBinder.removeBean();
    }

    public void setTourDeleteConsumer(SerializableConsumer<Tour> tourDeleteConsumer) {
        this.tourDeleteConsumer = tourDeleteConsumer;
    }
}
