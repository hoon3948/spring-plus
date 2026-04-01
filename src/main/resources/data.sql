/** */


INSERT INTO users (id, email, password, user_role, nickname, created_at, modified_at)
VALUES (1, 'admin@example.com', '$2a$04$8i8TF6uxNx8/XI6xYsHSlekuTN7JejVCAXnJB37ngDaceZKrc1AZS', 'ADMIN', 'admin',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'user@example.com', '$2a$04$8i8TF6uxNx8/XI6xYsHSlekuTN7JejVCAXnJB37ngDaceZKrc1AZS', 'USER', 'user',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO todos (id, title, contents, weather, user_id, created_at, modified_at)
VALUES (1, 'Admin Todo 1', 'Admin task details 1', 'sunny', 1, '2026-03-28T17:24:46.210588', '2026-03-28T17:24:46.210588'),
       (2, 'Admin Todo 2', 'Admin task details 2', 'cloudy', 1, '2026-03-29T17:24:46.210588', '2026-03-29T17:24:46.210588'),
       (3, 'Admin Todo 3', 'Admin task details 3', 'rainy', 1, '2026-03-30T17:24:46.210588', '2026-03-30T17:24:46.210588'),
       (4, 'User Todo 1', 'User task details 1', 'sunny', 2, '2026-03-31T07:24:46.210588', '2026-03-31T07:24:46.210588'),
       (5, 'User Todo 2', 'User task details 2', 'windy', 2, '2026-03-31T13:24:46.210588', '2026-03-31T13:24:46.210588'),
       (6, 'User Todo 3', 'User task details 3', 'snowy', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO managers (id, user_id, todo_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 2, 4),
       (5, 2, 5),
       (6, 2, 6);

INSERT INTO comments (id, contents, user_id, todo_id, created_at, modified_at)
VALUES (1, 'Comment 1 for todo 1', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'Comment 2 for todo 1', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'Comment 3 for todo 1', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, 'Comment 1 for todo 2', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'Comment 2 for todo 2', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, 'Comment 3 for todo 2', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (7, 'Comment 1 for todo 3', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (8, 'Comment 2 for todo 3', 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (9, 'Comment 3 for todo 3', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10, 'Comment 1 for todo 4', 2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (11, 'Comment 2 for todo 4', 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (12, 'Comment 3 for todo 4', 2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (13, 'Comment 1 for todo 5', 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (14, 'Comment 2 for todo 5', 2, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (15, 'Comment 3 for todo 5', 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (16, 'Comment 1 for todo 6', 2, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (17, 'Comment 2 for todo 6', 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (18, 'Comment 3 for todo 6', 2, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


ALTER TABLE users ALTER COLUMN id RESTART WITH 3;
ALTER TABLE todos ALTER COLUMN id RESTART WITH 7;
ALTER TABLE managers ALTER COLUMN id RESTART WITH 7;
ALTER TABLE comments ALTER COLUMN id RESTART WITH 19;
/**  */
