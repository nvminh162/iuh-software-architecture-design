package com.nvminh162.todo.query.service;

import com.nvminh162.todo.common.TodoMapper;
import com.nvminh162.todo.common.TodoStore;
import com.nvminh162.todo.query.model.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoQueryServiceImpl implements TodoQueryService {
    private final TodoStore todoStore;

    @Override
    public List<TodoResponse> getAll() {
        return todoStore.findAll().stream()
                .map(TodoMapper::toResponse)
                .sorted(Comparator.comparing(TodoResponse::getCreatedAt))
                .toList();
    }

    @Override
    public Optional<TodoResponse> getById(UUID id) {
        return todoStore.findById(id).map(TodoMapper::toResponse);
    }
}
