CREATE TABLE if not exists post
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    city_id INT
);

CREATE TABLE if not exists candidate
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     TIMESTAMP,
    photo       BYTEA
);

CREATE TABLE if not exists users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    VARCHAR unique,
    password TEXT
);