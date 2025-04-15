package com.svenruppert.expedition.planner.views.packing.participants;

import com.svenruppert.expedition.planner.components.AbstractCrudView;
import com.svenruppert.expedition.planner.data.entity.DietaryRestriction;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.Route;

import java.util.stream.Collectors;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.getOrCreateParticipantsService;

@Route(ParticipantsView.VIEW_NAME)
public class ParticipantsView extends AbstractCrudView<Participant, ParticipantService, VerticalLayout> {

    public static final String SUB_TITLE = "packing.participants.subtitle";
    public static final String VIEW_NAME = PackingMainLayout.PACKING_ROUTE + "participants";

    public ParticipantsView() {
        super(Participant.class, SUB_TITLE);

        getCrudGrid().getGrid().setColumns("name", "dailyCaloricRequirement", "restrictions");
        //getCrudGrid().getGrid().addColumn(createDietRestrictionRenderer()).setHeader("Diet Restriction");
        getCrudGrid().addEditIconColumn();
    }

    private LitRenderer<Participant> createDietRestrictionRenderer() {
        return LitRenderer.<Participant>of("<div>" +
                        "${dietRestrictions.map((restriction) => html` <span>${restriction}</span>`)}" +
                    "</div>")
                .withProperty("dietRestrictions", participant ->
                        participant.getRestrictions().stream()
                            .map(dietaryRestriction -> getTranslation(dietaryRestriction.getNameKey()))
                            .collect(Collectors.toSet()));
    }

    @Override
    protected ParticipantService getService() {
        return getOrCreateParticipantsService();
    }

    @Override
    protected Component createDialogForm(Binder<Participant> binder) {
        TextField nameField = new TextField(getTranslation("packing.participants.nameField.label"));
        nameField.setWidthFull();
        binder.bind(nameField, Participant::getName, Participant::setName);

        IntegerField caloriesField = new IntegerField(getTranslation("packing.participants.caloriesField.label"));
        caloriesField.setWidthFull();
        binder.bind(caloriesField, Participant::getDailyCaloricRequirement, Participant::setDailyCaloricRequirement);

        MultiSelectComboBox<DietaryRestriction> restrictionsField = new MultiSelectComboBox<>(getTranslation("packing.participants.dietaryRestrictionField.label"));
        restrictionsField.setItems(DietaryRestriction.values());
        binder.bind(restrictionsField, Participant::getRestrictions, Participant::setRestrictions);

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.add(nameField, caloriesField, restrictionsField);

        return rootLayout;
    }
}
