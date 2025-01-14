package com.svenruppert.expedition.planner;

import com.svenruppert.expedition.planner.components.AbstractView;
import com.svenruppert.expedition.planner.services.GreetService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PreserveOnRefresh
public class MainView
    extends AbstractView<VerticalLayout>
    implements LocaleChangeObserver {

  public static final String YOUR_NAME = "your.name";
  public static final String SAY_HELLO = "say.hello";
  public static final String SUB_TITLE = "mainview.subtitle";

  private GreetService greetService = new GreetService();

  private Button button = new Button();
  private TextField textField = new TextField();

  @Override
  public void localeChange(LocaleChangeEvent localeChangeEvent) {
    button.setText(getTranslation(SAY_HELLO));
    textField.setLabel(getTranslation(YOUR_NAME));
  }

  public MainView() {
    super(SUB_TITLE);
    button.addClickListener(e -> {
      getContent().add(new Paragraph(greetService.greet(textField.getValue())));
    });
    getContent().add(textField, button);
  }
}
