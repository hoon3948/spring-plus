package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Todo> findTodos(String weather, Pageable pageable, LocalDateTime startedAt, LocalDateTime endedAt) {
        BooleanBuilder builder = new BooleanBuilder();

        if (weather != null && !weather.isEmpty()) {
            builder.and(todo.weather.contains(weather));
        }
        if (startedAt != null) {
            builder.and(todo.modifiedAt.after(startedAt));
        }
        if (endedAt != null) {
            builder.and(todo.modifiedAt.before(endedAt));
        }

        // 1) fetch content
        List<Todo> content = queryFactory
                .selectFrom(todo)
                .where(builder)
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 2) total count
        Long total = queryFactory
                .select(todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(todo)
                        .leftJoin(todo.user).fetchJoin()
                        .where(todo.id.eq(todoId))
                        .fetchOne()
        );
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(Pageable pageable, String title, LocalDateTime startedAt, LocalDateTime endedAt, String nickname) {
        BooleanBuilder builder = new BooleanBuilder();

        if (title != null && !title.isEmpty()) {
            builder.and(todo.title.contains(title));
        }
        if (nickname != null && !nickname.isEmpty()) {
            builder.and(user.nickname.contains(nickname));
        }
        if (startedAt != null) {
            builder.and(todo.createdAt.after(startedAt));
        }
        if (endedAt != null) {
            builder.and(todo.createdAt.before(endedAt));
        }

        // 1) fetch content
        List<TodoSearchResponse> content = queryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.title,
                        todo.managers.size(),
                        todo.comments.size()
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(builder)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 2) total count
        Long total = queryFactory
                .select(todo.count())
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(builder)
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(content, pageable, total);
    }
}
