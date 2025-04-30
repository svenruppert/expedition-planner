package junit.com.svenruppert.expedition.planner.services.tour;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.data.entity.DietaryRestriction;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.UpdateEntityResponse;
import com.svenruppert.expedition.planner.services.persistence.DataRoot;
import com.svenruppert.expedition.planner.services.persistence.PersistenceService;
import com.svenruppert.expedition.planner.services.tour.TourService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TourServiceTest implements HasLogger {

    private static PersistenceService service ;
    private static TourService tourService;

    private static Path tempDir;

    @BeforeAll
    static void beforeAll(@TempDir Path tempDir) {
        TourServiceTest.tempDir = tempDir;

        HasLogger.staticLogger().info("TourServiceTest - before all");

        service = new PersistenceService();

        service.init(tempDir);

        DataRoot dataRoot = service.getDataRoot();
        var tourRepository = dataRoot.getTourRepository();

        tourService = new TourService(tourRepository);

        TourTestUtil.createTestTours(tourService);
    }

    @AfterAll
    static void afterAll() {
        HasLogger.staticLogger().info("TourServiceTest - after all");

        service.shutdown();
        service.close();
    }

    @Test
    void test001CreateTour() {
        // Arrange
        Set<Participant> participants = new HashSet<>();
        participants.add(
                TourTestUtil.createParticipant("Neuer Teilnehmer", 2400,
                        Set.of(DietaryRestriction.PALEO)));

        Tour newTour = new Tour("Neue Testtour", participants);

        int oldTourCount = tourService.all().size();

        // Act
        CreateEntityResponse<Tour> response = tourService.create(newTour);
        Set<Tour> allTours = tourService.all();

        // Assert
        assertTrue(response.created());
        assertNotNull(response.entity());

        assertEquals("Neue Testtour", response.entity().getName());
        assertEquals(1, response.entity().getParticipantSet().size());
        assertTrue(allTours.contains(newTour));
        assertEquals(oldTourCount + 1, allTours.size());
    }

    @Test
    void test002ReadTour() {
        // Arrange
        Set<Participant> participants = new HashSet<>();
        participants.add(
                TourTestUtil.createParticipant("Neuer Test Teilnehmer", 5000,
                        Set.of(DietaryRestriction.KETO)));

        var localTestTour = new Tour("Neue Testtour 2", participants);

        // Act
        CreateEntityResponse<Tour> response = tourService.create(localTestTour);
        Set<Tour> allTours = tourService.all();

        // Assert
        assertTrue(response.created());
        assertNotNull(allTours);
        assertFalse(allTours.isEmpty());

        Optional<Tour> optionalTour = allTours.stream()
                .filter(tour -> tour.getName().equals(localTestTour.getName()))
                .findFirst();

        assertTrue(optionalTour.isPresent(), "Tour " + localTestTour.getName() + " wurde nicht gefunden");

        optionalTour.ifPresent(tour -> {
            assertEquals(localTestTour.getName(), tour.getName(), "Tour name is not equal");
            assertEquals(localTestTour.getParticipantSet().size(), tour.getParticipantSet().size(),
                    "Number of participants in Tour " + localTestTour.getName() + " are not equal");
        });
    }

    //update
    @Test
    void test003UpdateTour() {
        // Arrange
        var oldTourList = tourService.all();
        assertFalse(oldTourList.isEmpty(), "No Tours found in database.");

        Tour tourToUpdate = oldTourList.iterator().next();
        String originalName = tourToUpdate.getName();
        String updatedName = originalName + " (updated)";

        // Add new Participant to Tour
        Set<Participant> updatedParticipants = new HashSet<>(tourToUpdate.getParticipantSet());
        updatedParticipants.add(TourTestUtil.createParticipant("Additional Participant", 2300,
                Set.of(DietaryRestriction.LOW_CARB)));

        // Act
        tourToUpdate.setName(updatedName);
        tourToUpdate.setParticipantSet(updatedParticipants);
        UpdateEntityResponse<Tour> response = tourService.save(tourToUpdate);
        var newTourList = tourService.all();

        // Assert
        assertTrue(response.updated());
        assertNotNull(response.value());

        assertEquals(updatedName, response.value().getName());
        assertEquals(updatedParticipants.size(), response.value().getParticipantSet().size());

        boolean updatedTourFound = newTourList.stream()
                .anyMatch(tour -> tour.getName().equals(updatedName));
        assertTrue(updatedTourFound, "Updated Tour not found");
    }

    //delete
    @Test
    void test004DeleteTour() {
        // Arrange
        var oldTourList = tourService.all();
        assertFalse(oldTourList.isEmpty(), "No Tours found in database.");

        Tour tourToDelete = oldTourList.iterator().next();
        int initialCount = tourService.all().size();

        // Act
        tourService.delete(tourToDelete);
        Set<Tour> remainingTours = tourService.all();

        // Assert
        assertEquals(initialCount - 1, remainingTours.size());
        boolean tourStillExists = remainingTours.stream()
                .anyMatch(tour -> tour.getName().equals(tourToDelete.getName()));
        assertFalse(tourStillExists, "Deleted Tour still exist");
    }
}