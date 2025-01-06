package com.svenruppert.expedition.planner.components;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;

public abstract class AbstractViewHeader
    extends Composite<VerticalLayout> {

  public AbstractViewHeader(String subTitle) {
    initView(subTitle);
  }

  protected RouterLink createLink(String viewName,
                                  Class<? extends Composite> viewClass) {
    RouterLink link = new RouterLink();
    link.add(viewName);
    link.setRoute(viewClass);
    link.addClassNames(LumoUtility.Display.FLEX,
        LumoUtility.AlignItems.CENTER,
        LumoUtility.Padding.Horizontal.MEDIUM,
        LumoUtility.TextColor.SECONDARY,
        LumoUtility.FontWeight.MEDIUM);
    link.getStyle().set("text-decoration", "none");
    return link;
  }

  public void initView(String subTitle) {
    DrawerToggle toggle = new DrawerToggle();
    H2 viewTitle = new H2(getTranslation(subTitle));
    viewTitle.getStyle()
        .set("font-size", "var(--lumo-font-size-l)")
        .set("margin", "0");
    HorizontalLayout titleBar = new HorizontalLayout(toggle, viewTitle);
    titleBar.setAlignItems(FlexComponent.Alignment.CENTER);
    titleBar.setSpacing(false);

    VerticalLayout viewHeader = getContent();
    viewHeader.add(titleBar, secondaryNavigation());
    viewHeader.setPadding(false);
    viewHeader.setSpacing(false);
  }

  private HorizontalLayout secondaryNavigation() {
    HorizontalLayout navigation = new HorizontalLayout();
    navigation.addClassNames(
        LumoUtility.JustifyContent.CENTER,
        LumoUtility.Gap.SMALL,
        LumoUtility.Height.MEDIUM);
    secondaryNavigationLinks().forEach(navigation::add);
    return navigation;
  }

  public abstract List<RouterLink> secondaryNavigationLinks();


}
