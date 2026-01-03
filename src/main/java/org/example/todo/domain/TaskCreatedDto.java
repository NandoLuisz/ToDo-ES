package org.example.todo.domain;

import java.time.LocalDateTime;

public record TaskCreatedDto(Long id, String title, String description, String priority, boolean isCompleted, LocalDateTime createdAt) {
}