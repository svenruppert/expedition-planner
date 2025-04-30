package com.svenruppert.expedition.planner.services.tour;

import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.DeleteEntityResponse;
import com.svenruppert.expedition.planner.services.SingletonRegistry;
import com.svenruppert.expedition.planner.services.UpdateEntityResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TourRepository {

    private final Set<Tour> entityHashSet = new HashSet<>();

    public CreateEntityResponse<Tour> create(Tour entity) {
        entityHashSet.add(entity);

        SingletonRegistry.getOrCreatePersistenceService().store(entityHashSet);
        SingletonRegistry.getOrCreatePersistenceService().store(entity.getParticipantSet());

        return new CreateEntityResponse<>(true, String.format("Tour %s successfully created", entity.getName()), entity);
    }

    public UpdateEntityResponse<Tour> update(Tour entity) {

        SingletonRegistry.getOrCreatePersistenceService().store(entity);
        SingletonRegistry.getOrCreatePersistenceService().store(entity.getParticipantSet());

        return new UpdateEntityResponse<>(true, String.format("Tour %s successfully updated", entity.getName()), entity);
    }

    public Set<Tour> getAll() {
        return entityHashSet;
    }

    public DeleteEntityResponse<Tour> delete(Tour entity) {
        entityHashSet.remove(entity);

        SingletonRegistry.getOrCreatePersistenceService().store(entityHashSet);

        return new DeleteEntityResponse<>(true, String.format("Tour %s successfully deleted", entity.getName()), entity);
    }

    public Set<Tour> getByName(String name) {
        return entityHashSet.stream()
                .filter(tour -> tour.getName().startsWith(name))
                .collect(Collectors.toSet());
    }
}
