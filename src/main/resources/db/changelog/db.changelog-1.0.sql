--liquibase formatted sql

--changeset iw:1
CREATE TABLE IF NOT EXISTS company
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);
--DROP TABLE company

--changeset iw:2
CREATE TABLE IF NOT EXISTS company_locales
(
    company_id INT REFERENCES company (id) ON DELETE CASCADE,
    lang VARCHAR(2),
    description VARCHAR(255) NOT NULL ,
    PRIMARY KEY (company_id, lang)
);
--DROP TABLE company-locales

--changeset iw:3
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(64) NOT NULL UNIQUE ,
    birth_date DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    role VARCHAR(32),
    company_id INT REFERENCES company (id)
);
--DROP TABLE users

--changeset iw:4
CREATE TABLE IF NOT EXISTS payment
(
    id BIGSERIAL PRIMARY KEY,
    amount INT NOT NULL ,
    receiver_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE
);
--DROP TABLE payment

--changeset iw:5
CREATE TABLE IF NOT EXISTS chat
(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);
--DROP TABLE chat

--changeset iw:6
CREATE TABLE IF NOT EXISTS users_chat
(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL REFERENCES users (id),
    chat_id BIGINT NOT NULL REFERENCES chat (id),
    UNIQUE (user_id, chat_id)
);
--DROP TABLE users_chat