package com.svenruppert.expedition.planner.data.entity;

import java.util.HashSet;
import java.util.Set;

public class Tour {

    private String name ;

    private Set<Participant> participantSet = new HashSet<>();

    public Tour(String name, Set<Participant> participantSet) {
        this.name = name;
        //https://docs.eclipsestore.io/manual/storage/addendum/specialized-type-handlers.html#jdk17
        this.participantSet.addAll(participantSet);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Participant> getParticipantSet() {
        return participantSet;
    }

    public void setParticipantSet(Set<Participant> participantSet) {
        //https://docs.eclipsestore.io/manual/storage/addendum/specialized-type-handlers.html#jdk17
        this.participantSet.retainAll(participantSet);
    }
}
