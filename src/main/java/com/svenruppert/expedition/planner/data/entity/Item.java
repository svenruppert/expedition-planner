package com.svenruppert.expedition.planner.data.entity;

public class Item {

    private String name;
    private boolean shared;
    private Participant responsibleForShared;

    public Item() {
    }

    public Item(String name, boolean shared) {
        this.name = name;
        this.shared = shared;
        this.responsibleForShared = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public Participant getResponsibleForShared() {
        return responsibleForShared;
    }

    public void setResponsibleForShared(Participant responsibleForShared) {
        this.responsibleForShared = responsibleForShared;
    }
}

