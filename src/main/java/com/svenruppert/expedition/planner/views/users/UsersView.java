package com.svenruppert.expedition.planner.views.users;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("users")
public class UsersView extends AbstractView<VerticalLayout> {

    public static final String MENU_ITEM_USERS = "mainlayout.menuitem.users";
    public static final String TITLE = "users.title";

    public UsersView() {
        super(TITLE);
        getContent().add(new H2(getTranslation(TITLE)));
    }
}
