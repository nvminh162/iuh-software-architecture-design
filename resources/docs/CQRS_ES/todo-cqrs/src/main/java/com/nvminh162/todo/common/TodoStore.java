package com.nvminh162.todo.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TodoStore {
    private final TodoJpaRepository todoJpaRepository;

    @Transactional
    public TodoRecord create(String title, String description) {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        TodoEntity entity = TodoEntity.builder()
                .id(id)
                .title(title)
                .description(description)
                .completed(false)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return toRecord(todoJpaRepository.save(entity));
    }

    @Transactional
    public Optional<TodoRecord> update(UUID id, String title, String description, boolean completed) {
        return todoJpaRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(title);
                    existing.setDescription(description);
                    existing.setCompleted(completed);
                    existing.setUpdatedAt(Instant.now());
                    return toRecord(todoJpaRepository.save(existing));
                });
    }

    @Transactional
    public boolean delete(UUID id) {
        if (!todoJpaRepository.existsById(id)) {
            return false;
        }
        todoJpaRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<TodoRecord> findAll() {
        return todoJpaRepository.findAll().stream()
                .map(TodoStore::toRecord)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<TodoRecord> findById(UUID id) {
        return todoJpaRepository.findById(id).map(TodoStore::toRecord);
    }

    private static TodoRecord toRecord(TodoEntity entity) {
        return new TodoRecord(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.isCompleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
