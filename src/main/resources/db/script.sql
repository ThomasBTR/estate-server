DROP DATABASE IF EXISTS ESTATE;

CREATE DATABASE ESTATE;

USE ESTATE;

CREATE TABLE USERS
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email      VARCHAR(255),
    name       VARCHAR(255),
    password   VARCHAR(255),
    role       VARCHAR(255),
    enabled    BOOLEAN   DEFAULT TRUE,
    created_at timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE TOKENS
(
    id         INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    token      VARCHAR(255),
    user_id    INTEGER                        NOT NULL,
    token_type VARCHAR(12)                    NOT NULL,
    revoked    BOOLEAN                        NOT NULL,
    expired    BOOLEAN                        NOT NULL
);

CREATE TABLE RENTALS
(
    id          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255),
    surface     numeric,
    price       numeric,
    picture     VARCHAR(255),
    description VARCHAR(2000),
    owner_id    INTEGER                        NOT NULL,
    created_at  timestamp,
    updated_at  timestamp default current_timestamp
);

CREATE TABLE MESSAGES
(
    id         INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    rental_id  INT,
    user_id    INTEGER                        NOT NULL,
    message    VARCHAR(2000),
    created_at timestamp,
    updated_at timestamp default current_timestamp
);

CREATE UNIQUE INDEX USERS_index ON USERS (email);

SET FOREIGN_KEY_CHECKS = 0;
ALTER TABLE RENTALS
    ADD FOREIGN KEY (owner_id) REFERENCES USERS (id);

ALTER TABLE MESSAGES ADD FOREIGN KEY (user_id) REFERENCES USERS (id);

ALTER TABLE TOKENS ADD FOREIGN KEY (user_id) REFERENCES USERS (id);

ALTER TABLE MESSAGES ADD FOREIGN KEY (rental_id) REFERENCES RENTALS (id);


