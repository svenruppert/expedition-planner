package com.svenruppert.expedition.planner.data.entity;

public class Participant {

    private String name ;

    public Participant() {
    }

    public Participant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
