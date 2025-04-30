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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TourPersistenceTest implements HasLogger {

    @Test
    void test001Create(@TempDir Path tempDir) {

        PersistenceService service = new PersistenceService();
        service.init(tempDir);

        DataRoot dataRoot = service.getDataRoot();
        var tourRepository = dataRoot.getTourRepository();
        var tourService = new TourService(tourRepository);

        Set<Participant> participants = new HashSet<>();
        participants.add(TourTestUtil.createParticipant("Peter Müller", 2300,
                Set.of(DietaryRestriction.GLUTEN_FREE)));
        participants.add(TourTestUtil.createParticipant("Maria Schneider", 1900,
                Set.of(DietaryRestriction.VEGAN)));

        var uniqueTourName = "Persistence-Test-Tour-" + UUID.randomUUID();
        Tour persistentedTour = TourTestUtil.createTour(uniqueTourName, participants);

        CreateEntityResponse<Tour> response = tourService.create(persistentedTour);
        assertTrue(response.created());

        service.shutdown();
        service.close();

        service.init(tempDir);

        tourService.all().forEach(tour -> logger().info("Tour: {}", tour.getName()));

        boolean newTourExists = tourService.all().stream().anyMatch(tour -> tour.getName().equals(persistentedTour.getName()));
        assertTrue(newTourExists, "Tour " + persistentedTour.getName() + " does not exist");
    }

    @Test
    void test002Update(@TempDir Path tempDir) {

        PersistenceService service = new PersistenceService();
        service.init(tempDir);

        DataRoot dataRoot = service.getDataRoot();
        var tourRepository = dataRoot.getTourRepository();
        var tourService = new TourService(tourRepository);

        TourTestUtil.createTestTours(tourService);

        Set<Tour> tours = tourService.all();
        assertFalse(tours.isEmpty(), "No Tours found in database.");

        Tour existingTour = tours.iterator().next();
        existingTour.getParticipantSet().add(TourTestUtil.createParticipant("Peter Müller", 2300,
                Set.of(DietaryRestriction.GLUTEN_FREE)));
        existingTour.getParticipantSet().add(TourTestUtil.createParticipant("Maria Schneider", 1900,
                Set.of(DietaryRestriction.VEGAN)));

        var uniqueTourName = "Persistence-Test-Tour-" + UUID.randomUUID();
        existingTour.setName(uniqueTourName);

        int participantCount = existingTour.getParticipantSet().size();

        UpdateEntityResponse<Tour> response = tourService.save(existingTour);
        assertTrue(response.updated());

        service.shutdown();
        service.close();

        service.init(tempDir);

        var tourOptional = tourService.all().stream()
                .filter(tour -> tour.getName().equals(existingTour.getName()))
                .findFirst();

        assertTrue(tourOptional.isPresent(), "Tour " + existingTour.getName() + " does not exist");

        Tour newTour = tourOptional.get();
        assertEquals(participantCount, newTour.getParticipantSet().size(), "Participant count is not correct");
    }
}