package com.svenruppert.expedition.planner.services;

public record CreateEntityResponse<T>(boolean created, String message, T entity) {
}
