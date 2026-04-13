package com.nvminh162.todo.command.model;

import lombok.Data;

@Data
public class CreateTodoRequest {
    private String title;
    private String description;
}
