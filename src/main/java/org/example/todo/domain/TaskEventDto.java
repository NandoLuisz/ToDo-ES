package org.example.todo.domain;

public record TaskEventDto(
        String type,
        Long taskId,
        TaskCreatedDto task
) {}

