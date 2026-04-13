package com.nvminh162.todo.command.service;

import com.nvminh162.todo.command.model.CreateTodoRequest;
import com.nvminh162.todo.command.model.UpdateTodoRequest;
import com.nvminh162.todo.common.TodoMapper;
import com.nvminh162.todo.common.TodoStore;
import com.nvminh162.todo.query.model.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoCommandServiceImpl implements TodoCommandService {
    private final TodoStore todoStore;

    @Override
    public TodoResponse create(CreateTodoRequest request) {
        return TodoMapper.toResponse(todoStore.create(request.getTitle(), request.getDescription()));
    }

    @Override
    public Optional<TodoResponse> update(UUID id, UpdateTodoRequest request) {
        return todoStore.update(id, request.getTitle(), request.getDescription(), request.isCompleted())
                .map(TodoMapper::toResponse);
    }

    @Override
    public boolean delete(UUID id) {
        return todoStore.delete(id);
    }
}
