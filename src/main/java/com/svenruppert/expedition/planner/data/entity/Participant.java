package com.svenruppert.expedition.planner.data.entity;

import java.util.HashSet;
import java.util.Set;

public class Participant {

    private String name ;
    private Integer dailyCaloricRequirement ;
    private Set<DietaryRestriction> restrictions = new HashSet<>();

    public Participant() {
    }

    public Participant(String name, Integer dailyCaloricRequirement, Set<DietaryRestriction> restrictions) {
        this.name = name;
        this.dailyCaloricRequirement = dailyCaloricRequirement;
        this.restrictions.addAll(restrictions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDailyCaloricRequirement() {
        return dailyCaloricRequirement;
    }

    public void setDailyCaloricRequirement(Integer dailyCaloricRequirement) {
        this.dailyCaloricRequirement = dailyCaloricRequirement;
    }

    public Set<DietaryRestriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Set<DietaryRestriction> restrictions) {
        this.restrictions.retainAll(restrictions);
    }
}
