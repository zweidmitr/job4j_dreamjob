CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    TEXT,
    password TEXT
);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);

INSERT INTO users (email) VALUES ('user@ya.ru'); -- сохранить данные.
INSERT INTO users (email) VALUES ('user@ya.ru'); -- вернеться с ошибкой ConstrainsViolationException.