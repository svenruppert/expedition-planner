package com.svenruppert.expedition.planner.views.packing.checklist;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.function.SerializableConsumer;

class ItemDialog extends Dialog {

    private final SerializableConsumer<CheckListView.Item> saveConsumer;
    private final SerializableConsumer<CheckListView.Item> deleteConsumer;

    private final Binder<CheckListView.Item> binder = new Binder<>(CheckListView.Item.class);

    public ItemDialog(
            SerializableConsumer<CheckListView.Item> saveConsumer,
            SerializableConsumer<CheckListView.Item> deleteConsumer) {
        this.saveConsumer = saveConsumer;
        this.deleteConsumer = deleteConsumer;

        TextField nameField = new TextField("Name");
        nameField.setWidthFull();
        binder.bind(nameField, "name");

        Checkbox sharedCheckbox = new Checkbox("Shared");
        binder.bind(sharedCheckbox, "shared");

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.add(nameField, sharedCheckbox);
        rootLayout.add(createButtonLayout());

        rootLayout.setPadding(false);
        rootLayout.setSizeFull();
        add(rootLayout);

        setModal(true);
    }

    public void openWithItem(CheckListView.Item item) {
        binder.readRecord(item);
        open();
    }

    private HorizontalLayout createButtonLayout() {
        Button deleteButton = new Button("delete");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.addClickListener(event -> {
            try {
                deleteConsumer.accept(binder.writeRecord());
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            this.close();
        });

        Button cancelButton = new Button("cancel");
        cancelButton.addClickListener(event -> {
            this.close();
        });

        Button saveButton = new Button("save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            try {
                saveConsumer.accept(binder.writeRecord());
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            this.close();
        });

        return new HorizontalLayout(
                deleteButton,
                cancelButton,
                saveButton);
    }
}
