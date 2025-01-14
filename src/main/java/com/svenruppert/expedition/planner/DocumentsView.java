package com.svenruppert.expedition.planner;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "documents")
public class DocumentsView extends AbstractView<HorizontalLayout> {

    public static final String MENU_ITEM_DOCUMENTS = "mainlayout.menuitem.documents";

    public DocumentsView() {
        super(MENU_ITEM_DOCUMENTS);
    }
}