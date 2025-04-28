package com.svenruppert.expedition.planner.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.lang.reflect.InvocationTargetException;

public class CrudGrid<T> extends Composite<Div> {

    protected static final String HUNDRED_PERCENT = "100%";
    private final Grid<T> grid ;
    private final Class<T> clazz;
    private final AbstractCrudDialog<T> dialog;

    public CrudGrid(Class<T> clazz, AbstractCrudDialog<T> dialog) {
        grid = new Grid<>(clazz);
        this.clazz = clazz;
        this.dialog = dialog;

        addEditIconColumn();

        Button addButton = new Button(VaadinIcon.PLUS_CIRCLE.create(), this::addButtonClicked);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClassNames(LumoUtility.Width.LARGE, LumoUtility.Height.LARGE, LumoUtility.BorderRadius.FULL);

        getContent().add(grid, addButton);
        getContent().setWidth(HUNDRED_PERCENT);
        getContent().setHeight(HUNDRED_PERCENT);
    }

    public void addEditIconColumn() {
        grid.addComponentColumn(this::createOpenEntityDialogIcon)
                .setTextAlign(ColumnTextAlign.CENTER)
                .setFlexGrow(0);
    }

    private Component createOpenEntityDialogIcon(T t) {
        Button openDialogButton = new Button(VaadinIcon.PENCIL.create(), event -> {
            dialog.openWithItem(t);
        });
        openDialogButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return openDialogButton;
    }

    private void addButtonClicked(ClickEvent<Button> buttonClickEvent) {
        try {
            dialog.openWithItem(clazz.getConstructor().newInstance());
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Grid<T> getGrid() {
        return grid;
    }
}
