package com.nvminh162.todo.common;

import com.nvminh162.todo.query.model.TodoResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoMapper {
    public static TodoResponse toResponse(TodoRecord record) {
        TodoResponse response = new TodoResponse();
        response.setId(record.getId());
        response.setTitle(record.getTitle());
        response.setDescription(record.getDescription());
        response.setCompleted(record.isCompleted());
        response.setCreatedAt(record.getCreatedAt());
        response.setUpdatedAt(record.getUpdatedAt());
        return response;
    }
}
