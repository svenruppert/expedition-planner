package com.svenruppert.expedition.planner.services.tour;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.CreateEntityResponse;
import com.svenruppert.expedition.planner.services.UpdateEntityResponse;

import java.util.Set;

public class TourService implements HasLogger {

    private final TourRepository repository;

    public TourService(TourRepository repository) {
        this.repository = repository;
    }

    public UpdateEntityResponse<Tour> save(Tour entity) {
        logger().info("Update tour {}", entity);
        return repository.update(entity);
    }

    public void delete(Tour entity) {
        logger().info("Deleting tour {}", entity);
        repository.delete(entity);
    }

    public Set<Tour> all() {
        logger().info("Fetching all tours ...");
        return repository.getAll();
    }

    public CreateEntityResponse<Tour> create(Tour tour) {
        logger().info("Create tour {}", tour);
        return repository.create(tour);
    }
}
