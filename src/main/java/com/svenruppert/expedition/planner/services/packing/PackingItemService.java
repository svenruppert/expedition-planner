package com.svenruppert.expedition.planner.services.packing;

import com.svenruppert.expedition.planner.data.entity.Item;
import com.svenruppert.expedition.planner.services.persistence.AbstractRepository;
import com.svenruppert.expedition.planner.services.persistence.AbstractService;

import java.util.ArrayList;
import java.util.List;

public class PackingItemService extends AbstractService<Item> {

    private static List<Item> itemList = new ArrayList<>(
            List.of(
                    new Item("Isomatte", false),
                    new Item("Zelt", true),
                    new Item("Schlafsack", false))
    );

    public PackingItemService(AbstractRepository<Item> repository) {
        super(repository);

        if (repository.getAll().isEmpty()) {
            itemList.forEach(repository::add);
            repository.saveRepository();
        }
    }
}
