package com.svenruppert.expedition.planner.services.persistence;

import com.svenruppert.expedition.planner.services.SingletonRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRepository<E> {

    private final Set<E> entityHashSet = new HashSet<>();

    public void add(E entity) {
        entityHashSet.add(entity);
    }

    public Collection<E> getAll() {
        return Collections.unmodifiableCollection(entityHashSet);
    }

    public void delete(E entity) {
        entityHashSet.remove(entity);
    }

    public void saveRepository() {
        SingletonRegistry.getOrCreatePersistenceService().store(entityHashSet);
    }
}
