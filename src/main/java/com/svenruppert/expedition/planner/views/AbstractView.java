package com.svenruppert.expedition.planner.views;

import com.svenruppert.expedition.planner.MainLayout;
import com.svenruppert.expedition.planner.components.AbstractViewHeader;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

public abstract class AbstractView<T extends Component>
    extends Composite<T>
    implements LocaleChangeObserver, BeforeEnterObserver {

  protected abstract AbstractViewHeader createViewHeader();

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    event.getUI()
        .getChildren()
        .filter(c -> c instanceof MainLayout)
        .findFirst()
        .ifPresent(component -> {
          ((MainLayout) component)
              .updateSecondaryNavigation(createViewHeader());
        });
  }
}
