package junit.com.svenruppert.expedition.planner.services.tour;

import com.svenruppert.expedition.planner.data.entity.DietaryRestriction;
import com.svenruppert.expedition.planner.data.entity.Participant;
import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.tour.TourService;

import java.util.List;
import java.util.Set;

class TourTestUtil {

    static List<Tour> createTestTours(TourService tourService) {
        // Teilnehmer erstellen
        Participant max = createParticipant("Max Mustermann", 2500,
                Set.of(DietaryRestriction.LACTOSE_FREE));

        Participant anna = createParticipant("Anna Schmidt", 2000,
                Set.of(DietaryRestriction.VEGETARIAN, DietaryRestriction.GLUTEN_FREE));

        Participant klaus = createParticipant("Klaus Weber", 3000,
                Set.of(DietaryRestriction.NUT_ALLERGY));

        Participant sarah = createParticipant("Sarah Müller", 2200,
                Set.of(DietaryRestriction.VEGAN));

        Participant thomas = createParticipant("Thomas Fischer", 2800,
                Set.of(DietaryRestriction.KETO));

        Participant julia = createParticipant("Julia Becker", 1900,
                Set.of(DietaryRestriction.PESCATARIAN, DietaryRestriction.HISTAMINE_INTOLERANT));

        // 5 Testtouren mit Teilnehmern erstellen
        var testTours = List.of(
                createTour("Alpenüberquerung", Set.of(max, anna, klaus)),
                createTour("Schwarzwald-Tour", Set.of(anna, sarah, julia)),
                createTour("Klettertour Dolomiten", Set.of(klaus, thomas)),
                createTour("Radtour Bodensee", Set.of(max, sarah, julia, thomas)),
                createTour("Wanderung Bayerischer Wald", Set.of(anna, max))
        );

        // Touren in die Datenbank einfügen
        for (Tour tour : testTours) {
            tourService.create(tour);
        }

        return testTours;
    }

    static Tour createTour(String name, Set<Participant> participants) {
        return new Tour(name, participants);
    }

    static Participant createParticipant(String name, int calories, Set<DietaryRestriction> restrictions) {
        return new Participant(name, calories, restrictions);
    }
}
