package com.svenruppert.expedition.planner.views.packing.itemlist;

import com.svenruppert.expedition.planner.components.AbstractCrudDialog;
import com.svenruppert.expedition.planner.data.entity.Item;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableConsumer;

class ItemDialog extends AbstractCrudDialog<Item> {

    public ItemDialog(
            SerializableConsumer<Item> saveConsumer,
            SerializableConsumer<Item> deleteConsumer) {
        super(Item.class, saveConsumer, deleteConsumer);
    }

    @Override
    protected Component createForm() {
        TextField nameField = new TextField("Name"); //TODO i18n
        nameField.setWidthFull();
        getBinder().bind(nameField, "name");

        Checkbox sharedCheckbox = new Checkbox("Shared"); //TODO i18n
        getBinder().bind(sharedCheckbox, "shared");

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.add(nameField, sharedCheckbox);

        return rootLayout;
    }

    public void openWithItem(Item item) {
        getBinder().setBean(item);
        open();
    }
}
