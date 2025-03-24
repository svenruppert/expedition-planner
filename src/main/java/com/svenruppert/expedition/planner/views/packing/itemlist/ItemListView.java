package com.svenruppert.expedition.planner.views.packing.itemlist;

import com.svenruppert.expedition.planner.components.AbstractCrudView;
import com.svenruppert.expedition.planner.components.CrudGrid;
import com.svenruppert.expedition.planner.data.entity.Item;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import static com.svenruppert.expedition.planner.services.SingletonRegistry.*;

@Route(ItemListView.VIEW_ROUTE)
public class ItemListView extends AbstractCrudView<Item, ItemService, VerticalLayout> {

    public static final String SUB_TITLE = "packing.itemlist.subtitle";
    public static final String VIEW_ROUTE = PackingMainLayout.PACKING_ROUTE + "itemlist";

    public ItemListView() {
        super(Item.class, SUB_TITLE);

        CrudGrid<Item> crudGrid = getCrudGrid();
        crudGrid.getGrid().removeAllColumns();
        crudGrid.getGrid().setColumns("name", "shared");
        crudGrid.getGrid().getColumnByKey("name").setHeader(getTranslation("packing.itemlist.grid.column.name"));
        crudGrid.getGrid().getColumnByKey("shared").setHeader(getTranslation("packing.itemlist.grid.column.shared"));

        crudGrid.getGrid()
                .addColumn(item -> item.getResponsibleForShared() != null ?
                        item.getResponsibleForShared().getName() : "")
                .setSortable(true)
                .setHeader(getTranslation("packing.itemlist.grid.column.responsibleForShared"));

        crudGrid.addEditIconColumn();
    }

    @Override
    protected ItemService getService() {
        return getOrCreateItemService();
    }

    @Override
    protected Component createDialogForm(Binder binder) {
        TextField nameField = new TextField(getTranslation("packing.itemlist.dialog.name.label"));
        nameField.setWidthFull();
        binder.bind(nameField, "name");

        Checkbox sharedCheckbox = new Checkbox(getTranslation("packing.itemlist.dialog.shared.label"));
        binder.bind(sharedCheckbox, "shared");

        ComboBox<Participant> participantComboBox = new ComboBox<>(getTranslation("packing.itemlist.dialog.participant.label"));
        participantComboBox.setItems(getOrCreateParticipantsService().all());
        participantComboBox.setEnabled(false);
        participantComboBox.setItemLabelGenerator(Participant::getName);
        binder.bind(participantComboBox, "responsibleForShared");

        sharedCheckbox.addValueChangeListener(event -> {
            participantComboBox.setEnabled(sharedCheckbox.getValue());
        });

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.add(nameField, sharedCheckbox, participantComboBox);

        return rootLayout;
    }
}
