package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoCustomRepository {
    Page<Todo> searchTodos(String weather, Pageable pageable, LocalDateTime createdAt, LocalDateTime modifiedAt);
    Optional<Todo> findByIdWithUser(Long todoId);
}
