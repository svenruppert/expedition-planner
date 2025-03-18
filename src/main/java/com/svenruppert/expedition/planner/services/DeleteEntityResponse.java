package com.svenruppert.expedition.planner.services;

public record DeleteEntityResponse<T>(boolean deleted, String message, T value) {
}
