package com.svenruppert.expedition.planner.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.HasDynamicTitle;

public abstract class AbstractView<T extends Component>
    extends Composite<T>
    implements LocaleChangeObserver, HasDynamicTitle {

  private final String subTitleKey;

  public AbstractView(String subTitleKey) {
    this.subTitleKey = subTitleKey;
  }

  @Override
  public String getPageTitle() {
    return !subTitleKey.isBlank() ?
            getTranslation(subTitleKey) : "";
  }

  @Override
  public void localeChange(LocaleChangeEvent event) {

  }
}
