package com.nvminh162.todo.query.controller;

import com.nvminh162.todo.query.model.TodoResponse;
import com.nvminh162.todo.query.service.TodoQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoQueryController {
    private final TodoQueryService todoQueryService;

    @GetMapping
    public List<TodoResponse> getAll() {
        return todoQueryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getById(@PathVariable UUID id) {
        return todoQueryService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
