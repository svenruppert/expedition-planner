package com.svenruppert.expedition.planner;

import com.svenruppert.expedition.planner.components.AbstractViewHeader;
import com.svenruppert.expedition.planner.views.AbstractView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@Route(value = "documents", layout = MainLayout.class)
@PreserveOnRefresh
public class DocumentsView extends AbstractView<HorizontalLayout>
    implements LocaleChangeObserver {
  @Override
  protected AbstractViewHeader createViewHeader() {
    return null;
  }

  @Override
  public void localeChange(LocaleChangeEvent event) {

  }
}
