package com.svenruppert.expedition.planner.services.tour;

import com.svenruppert.expedition.planner.data.entity.Tour;
import com.svenruppert.expedition.planner.services.persistence.AbstractService;

public class TourService extends AbstractService<Tour>  {

    public TourService(TourRepository repository) {
        super(repository);
    }
}
