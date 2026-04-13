package com.nvminh162.todo.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, UUID> {
}
