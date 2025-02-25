package com.svenruppert.expedition.planner.data.entity;

public class Assignment {

    private Participant participant;
    private Item item;

    public Assignment() {
    }

    public Assignment(Participant participant, Item item) {
        this.participant = participant;
        this.item = item;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
