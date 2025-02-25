package com.svenruppert.expedition.planner.views.packing.participants;

import com.svenruppert.expedition.planner.components.AbstractCrudDialog;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableConsumer;

class ParticipantsDialog extends AbstractCrudDialog<Participant> {

    public ParticipantsDialog(SerializableConsumer<Participant> saveConsumer, SerializableConsumer<Participant> deleteConsumer) {
        super(Participant.class, saveConsumer, deleteConsumer);
    }

    @Override
    protected Component createForm() {
        TextField nameField = new TextField("Name");
        nameField.setWidthFull();
        getBinder().bind(nameField, Participant::getName, Participant::setName);

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.add(nameField);

        return rootLayout;
    }
}
