package com.nvminh162.todo.query.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class TodoResponse {
    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private Instant createdAt;
    private Instant updatedAt;
}
