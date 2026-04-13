package com.nvminh162.todo.command.model;

import lombok.Data;

@Data
public class UpdateTodoRequest {
    private String title;
    private String description;
    private boolean completed;
}
