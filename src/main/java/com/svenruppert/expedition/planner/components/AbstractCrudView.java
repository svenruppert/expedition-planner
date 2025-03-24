package com.svenruppert.expedition.planner.components;

import com.svenruppert.expedition.planner.services.persistence.AbstractService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.data.binder.Binder;

public abstract class AbstractCrudView<E, S extends AbstractService<E>, C extends Component> extends AbstractView<C> {

    private final S service;

    private final CrudGrid<E> crudGrid;
    private final AbstractCrudDialog<E> crudDialog;

    private final Binder<E> binder;

    public AbstractCrudView(Class<E> entityClass, String subTitle) {
        super(subTitle);

        service = getService() ;
        binder = new Binder<>(entityClass);

        crudDialog = new AbstractCrudDialog<>(entityClass, binder, this::saveItem, this::deleteItem) {
            @Override
            protected Component createFormLayout() {
                return createDialogForm(binder);
            }
        };

        crudGrid = new CrudGrid<>(entityClass, crudDialog);

        crudGrid.getGrid().setItems(service.all());

        if (getContent() instanceof HasComponents) {
            ((HasComponents) getContent()).add(crudGrid);
        }

        if (getContent() instanceof HasSize) {
            ((HasSize) getContent()).setSizeFull();
        }
    }

    private void deleteItem(E entity) {
        service.delete(entity);
        service.saveRepository();
        crudGrid.getGrid().getDataProvider().refreshAll();
    }

    private void saveItem(E entity) {
        var entityPersisted = service.all().contains(entity);

        service.add(entity);
        service.saveRepository();

        if (entityPersisted)
            crudGrid.getGrid().getDataProvider().refreshItem(entity);
        else
            crudGrid.getGrid().getDataProvider().refreshAll();
    }

    public CrudGrid<E> getCrudGrid() {
        return crudGrid;
    }

    protected abstract S getService();

    protected abstract Component createDialogForm(Binder<E> binder);
}
