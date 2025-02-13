package com.svenruppert.expedition.planner.views.packing.checklist;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.List;

@Route(CheckListView.VIEW_ROUTE)
public class CheckListView extends AbstractView<VerticalLayout> {

    public static final String SUB_TITLE = "packing.checklist.subtitle";
    public static final String VIEW_ROUTE = PackingMainLayout.PACKING_ROUTE + "checklist";

    private final Grid<Item> itemGrid = new Grid<>(Item.class);

    public record Item(String name, Boolean shared) {
    };

    List<Item> itemList = new ArrayList<>(
            List.of(
            new Item("Isomatte", false),
            new Item("Zelt", true),
            new Item("Schlafsack", false))
    );

    ItemDialog itemDialog = new ItemDialog(
            this::saveItem,
            this::deleteItem
    );

    public CheckListView() {
        super(SUB_TITLE);

        getContent().add(new H1("Hello World"));

        itemGrid.addComponentColumn(this::createOpenItemDialog)
                .setTextAlign(ColumnTextAlign.CENTER)
                .setFlexGrow(0);
        itemGrid.setItems(itemList);

        Button addButton = new Button(VaadinIcon.PLUS_CIRCLE.create(), this::addButtonClicked);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClassNames(LumoUtility.Width.LARGE, LumoUtility.Height.LARGE, LumoUtility.BorderRadius.FULL);
        getContent()
                .add(itemGrid, addButton);
    }

    private Component createOpenItemDialog(Item item) {
        Button openDialog = new Button(VaadinIcon.PENCIL.create(), event -> itemDialog.openWithItem(item));
        openDialog.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return openDialog;
    }

    private void addButtonClicked(ClickEvent<Button> buttonClickEvent) {
        itemDialog.openWithItem(new Item("", false));
    }

    private void saveItem(Item item) {
        itemList.add(item);
        itemGrid.getDataProvider().refreshAll();
    }

    private void deleteItem(Item item) {
        itemList.remove(item);
        itemGrid.getDataProvider().refreshAll();
    }

}
