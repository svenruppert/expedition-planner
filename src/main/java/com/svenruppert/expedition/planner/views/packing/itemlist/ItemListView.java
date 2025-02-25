package com.svenruppert.expedition.planner.views.packing.itemlist;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.components.CrudGrid;
import com.svenruppert.expedition.planner.data.entity.Item;
import com.svenruppert.expedition.planner.views.packing.PackingMainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(ItemListView.VIEW_ROUTE)
public class ItemListView extends AbstractView<VerticalLayout> {

    public static final String SUB_TITLE = "packing.itemlist.subtitle";
    public static final String VIEW_ROUTE = PackingMainLayout.PACKING_ROUTE + "itemlist";
    private final CrudGrid<Item> grid;

    public static List<Item> itemList = new ArrayList<>(
            List.of(
                new Item("Isomatte", false),
                new Item("Zelt", true),
                new Item("Schlafsack", false))
    );

    public ItemListView() {
        super(SUB_TITLE);

        grid = new CrudGrid<>(Item.class, new ItemDialog(
                this::saveItem,
                this::deleteItem
        ));
        grid.getGrid().setItems(itemList);

        getContent().add(grid);
    }

    private void saveItem(Item item) {
        if (!itemList.contains(item)) {
            itemList.add(item);
        }
        grid.getGrid().getDataProvider().refreshAll();
    }

    private void deleteItem(Item item) {
        itemList.remove(item);
        grid.getGrid().getDataProvider().refreshAll();
    }

}
