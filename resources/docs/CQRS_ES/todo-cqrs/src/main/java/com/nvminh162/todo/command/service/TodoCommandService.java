package com.nvminh162.todo.command.service;

import com.nvminh162.todo.command.model.CreateTodoRequest;
import com.nvminh162.todo.command.model.UpdateTodoRequest;
import com.nvminh162.todo.query.model.TodoResponse;

import java.util.Optional;
import java.util.UUID;

public interface TodoCommandService {
    TodoResponse create(CreateTodoRequest request);

    Optional<TodoResponse> update(UUID id, UpdateTodoRequest request);

    boolean delete(UUID id);
}
