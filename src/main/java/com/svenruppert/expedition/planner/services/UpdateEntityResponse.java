package com.svenruppert.expedition.planner.services;

public record UpdateEntityResponse<T>(boolean updated, String message, T value) {
}
