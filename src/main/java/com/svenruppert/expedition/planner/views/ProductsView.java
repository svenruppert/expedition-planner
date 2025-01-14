package com.svenruppert.expedition.planner.views;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "products")
public class ProductsView extends AbstractView<HorizontalLayout> {

    public static final String MENU_ITEM_PRODUCTS = "mainlayout.menuitem.products";

    public ProductsView() {
        super(MENU_ITEM_PRODUCTS);
    }
}
