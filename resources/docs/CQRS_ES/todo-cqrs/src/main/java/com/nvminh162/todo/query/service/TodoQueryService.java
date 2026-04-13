package com.nvminh162.todo.query.service;

import com.nvminh162.todo.query.model.TodoResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoQueryService {
    List<TodoResponse> getAll();

    Optional<TodoResponse> getById(UUID id);
}
