package com.svenruppert.expedition.planner.services.persistence;

import com.svenruppert.dependencies.core.logger.HasLogger;

import java.util.Collection;

public class AbstractService<E> implements HasLogger {

    protected AbstractRepository<E> repository;

    public AbstractService(AbstractRepository<E> repository) {
        this.repository = repository;
    }

    public void add(E entity) {
      logger().info("Adding entity {}", entity);
        repository.add(entity);
    }

    public void delete(E entity) {
      logger().info("Deleting entity {}", entity);
        repository.delete(entity);
    }

    public Collection<E> all() {
        logger().info("Fetching all Entities ...");
        return repository.getAll();
    }

    public void saveRepository() {
        repository.saveRepository();
    }
}
