package com.svenruppert.expedition.planner.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.SerializableConsumer;

public abstract class AbstractCrudDialog<T> extends Dialog {

    private final SerializableConsumer<T> saveConsumer;
    private final SerializableConsumer<T> deleteConsumer;

    private final Binder<T> binder ;

    public AbstractCrudDialog(Class<T> clazz, SerializableConsumer<T> saveConsumer, SerializableConsumer<T> deleteConsumer) {
        this.saveConsumer = saveConsumer;
        this.deleteConsumer = deleteConsumer;
        this.binder = new Binder<>(clazz);

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.add(createForm());
        rootLayout.add(createButtonLayout());

        rootLayout.setPadding(false);
        rootLayout.setSizeFull();
        add(rootLayout);

        setModal(true);
    }

    protected abstract Component createForm();

    public Binder<T> getBinder() {
        return binder;
    }

    public void openWithItem(T entity) {
        binder.setBean(entity);
        open();
    }

    private HorizontalLayout createButtonLayout() {
        Button deleteButton = new Button("delete");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.addClickListener(event -> {
            deleteConsumer.accept(binder.getBean());
            this.close();
        });

        Button cancelButton = new Button("cancel");
        cancelButton.addClickListener(event -> {
            this.close();
        });

        Button saveButton = new Button("save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            if (!binder.validate().hasErrors()) {
                saveConsumer.accept(binder.getBean());
                this.close();
            }
        });

        return new HorizontalLayout(
                deleteButton,
                cancelButton,
                saveButton);
    }
}
