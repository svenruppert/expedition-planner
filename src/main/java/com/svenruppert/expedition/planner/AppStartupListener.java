package com.svenruppert.expedition.planner;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.data.entity.DietaryRestriction;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.SingletonRegistry;
import com.svenruppert.expedition.planner.services.persistence.PersistenceService;
import com.svenruppert.expedition.planner.services.user.User;
import com.svenruppert.expedition.planner.services.user.UserService;
import com.svenruppert.expedition.planner.services.packing.ParticipantService;
import com.svenruppert.expedition.planner.services.tour.TourService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.List;
import java.util.Set;

import static com.svenruppert.expedition.planner.services.user.UserRepository.ANONYMOUS_USER;

@WebListener
public class AppStartupListener implements ServletContextListener, HasLogger {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    logger().info("Webanwendung wird gestartet...");
    //TODO Security Issue
    logger().warn("Checking for admin account..");
    UserService userService = SingletonRegistry.getOrCreateUserService();
    User admin = userService.userByUserName("admin");
    if (admin.equals(ANONYMOUS_USER)) {
      logger().info("No admin found.");
      CreateEntityResponse<User> adminResponse
          = userService.createUser("admin", "admin", "Admin", "Admin");
      logger().warn("DEFAULT Admin created: {}", adminResponse);
      userService.saveRepository();
    } else {
      logger().info("Admin account already exists.");
    }

    checkAndCreateParticipants();
    checkAndCreateTours();

  }

  //TODO - extract DemoData Init
  private void checkAndCreateTours() {
    logger().info("Checking for Tours ...");
    TourService tourService = SingletonRegistry.getOrCreateTourService();
    if (tourService.all().isEmpty()) {
      logger().info("Creating default Tours");
      List.of(
                new Tour("Norway - Gjendesheim", Set.of()),
                new Tour("Iceland - Skaftafell", Set.of()),
                new Tour("Germany - Saxon Switzerland National Park", Set.of())
              ).forEach(tourService::add);
      tourService.saveRepository();
    } else {
      logger().info("Tours already exists.");
    }
  }

  private void checkAndCreateParticipants() {
    logger().info("Checking for participants...");
    ParticipantService participantService = SingletonRegistry.getOrCreateParticipantsService();
    if (participantService.all().isEmpty()) {
      logger().info("Creating default participants");
      List.of(
              new Participant("Alice", 2000, Set.of(DietaryRestriction.EGG_ALLERGY,
                      DietaryRestriction.LACTOSE_FREE, DietaryRestriction.FRUCTOSE_INTOLERANT)),
              new Participant("Bob", 3000, Set.of(DietaryRestriction.HISTAMINE_INTOLERANT)),
              new Participant("Carl", 2750, Set.of(DietaryRestriction.VEGAN)))
          .forEach(participantService::add);
      participantService.saveRepository();
    } else {
      logger().info("Participants already exists.");
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    logger().info("Webanwendung wird heruntergefahren...");
    PersistenceService persistenceService = SingletonRegistry.getInstance(PersistenceService.class);
    persistenceService.shutdown();
  }
}
