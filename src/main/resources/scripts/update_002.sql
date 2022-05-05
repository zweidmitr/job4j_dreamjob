CREATE TABLE candidate
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     TIMESTAMP,
    photo       BYTEA
);

drop TABLE candidate