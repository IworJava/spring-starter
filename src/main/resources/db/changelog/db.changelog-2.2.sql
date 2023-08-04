--liquibase formatted sql

--changeset iw:1
ALTER TABLE users_aud
ALTER COLUMN username DROP NOT NULL;