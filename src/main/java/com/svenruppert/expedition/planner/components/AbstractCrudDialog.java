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

    public AbstractCrudDialog(Class<T> clazz, Binder<T> binder, SerializableConsumer<T> saveConsumer, SerializableConsumer<T> deleteConsumer) {
        this.saveConsumer = saveConsumer;
        this.deleteConsumer = deleteConsumer;
        this.binder = binder;

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.add(createFormLayout());
        rootLayout.add(createButtonLayout());

        rootLayout.setPadding(false);
        rootLayout.setSizeFull();
        add(rootLayout);

        setModal(true);
    }

    protected abstract Component createFormLayout();

    public Binder<T> getBinder() {
        return binder;
    }

    public void openWithItem(T entity) {
        binder.setBean(entity);
        open();
    }

    private HorizontalLayout createButtonLayout() {
        Button deleteButton = new Button(getTranslation("cruddialog.delete.label"));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.addClickListener(event -> {
            deleteConsumer.accept(binder.getBean());
            this.close();
        });

        Button cancelButton = new Button(getTranslation("cruddialog.cancel.label"));
        cancelButton.addClickListener(event -> {
            this.close();
        });

        Button saveButton = new Button(getTranslation("cruddialog.save.label"));
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
