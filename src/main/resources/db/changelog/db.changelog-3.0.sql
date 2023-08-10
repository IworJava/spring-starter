--liquibase formatted sql

--changeset iw:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

--changeset iw:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);
