package com.svenruppert.expedition.planner.views.orders;

import com.svenruppert.expedition.planner.components.AbstractViewHeader;
import com.svenruppert.expedition.planner.views.AbstractView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.RouterLink;

import java.util.List;

public abstract class AbstractOrdersView
    extends AbstractView<HorizontalLayout>
    implements LocaleChangeObserver {

  public static final String CANCELLED = "orders.subnavigation.cancelled";
  public static final String COMPLETED = "orders.subnavigation.completed";
  public static final String OPEN = "orders.subnavigation.open";
  public static final String ALL = "orders.subnavigation.all";

  private final String subTitle;

  public AbstractOrdersView(String subTitle) {
    this.subTitle = subTitle;
    //I18NProvider i18NProvider =
  }

  @Override
  protected AbstractViewHeader createViewHeader() {
    return new AbstractViewHeader(subTitle) {
      @Override
      public List<RouterLink> secondaryNavigationLinks() {
        return List.of(
            createLink(getTranslation(ALL), AllOrdersView.class),
            createLink(getTranslation(OPEN), OpenOrdersView.class),
            createLink(getTranslation(COMPLETED), CompletedOrdersView.class),
            createLink(getTranslation(CANCELLED), CancelledOrdersView.class));
      }
    };
  }
}
