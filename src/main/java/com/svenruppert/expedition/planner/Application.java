package com.svenruppert.expedition.planner;

import com.vaadin.flow.server.VaadinServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Application {

  private Application() {
  }

  public static void main(String[] args) throws Exception {
// Jetty-Server initialisieren
    Server server = new Server(8080);

    // Servlet-Kontext konfigurieren
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    // Vaadin-Servlet hinzufügen
    ServletHolder vaadinServlet = new ServletHolder(new VaadinServlet());
    vaadinServlet.setInitParameter("ui", "com.svenruppert.expedition.planner.MainView"); // Ersetze "com.example.MyUI" mit deinem UI-Pfad
    vaadinServlet.setInitOrder(1);
    context.addServlet(vaadinServlet, "/*");

    // Server starten
    server.start();
    server.join();
  }
}
