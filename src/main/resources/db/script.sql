DROP DATABASE IF EXISTS ESTATE;

CREATE DATABASE ESTATE;
USE ESTATE;

CREATE TABLE RENTALS
(
    id          INTEGER AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255),
    surface     numeric,
    price       numeric,
    picture     VARCHAR(255),
    description VARCHAR(2000),
    owner_id    INTEGER,
    created_at  timestamp,
    updated_at  timestamp default current_timestamp,
    PRIMARY KEY (id)
);
CREATE TABLE MESSAGES
(
    id         INTEGER AUTO_INCREMENT NOT NULL,
    rental_id  INT,
    user_id    INTEGER,
    message    VARCHAR(2000),
    created_at timestamp,
    updated_at timestamp default current_timestamp,
    PRIMARY KEY (id)
);
CREATE TABLE TOKENS
(
    id         INTEGER AUTO_INCREMENT NOT NULL,
    token      VARCHAR(255),
    user_id    INTEGER,
    token_type VARCHAR(12)            NOT NULL,
    revoked    TINYINT(1)             NOT NULL DEFAULT 0,
    expired    TINYINT(1)             NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE USERS
(
    id         INTEGER AUTO_INCREMENT NOT NULL,
    email      VARCHAR(255),
    name       VARCHAR(255),
    password   VARCHAR(255),
    role       VARCHAR(255),
    enabled    TINYINT(1)             NOT NULL DEFAULT 1,
    created_at timestamp,
    updated_at timestamp                       default current_timestamp,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX USERS_index ON USERS (email);

ALTER TABLE RENTALS
    ADD CONSTRAINT FKh1czqhugq9sp457gm8oycrsr
        FOREIGN KEY (owner_id) REFERENCES USERS (id);

ALTER TABLE MESSAGES
    ADD CONSTRAINT FKh1gwf3xqzvcesvuim8oycrsr
        FOREIGN KEY (user_id) REFERENCES USERS (id);

ALTER TABLE MESSAGES
    ADD CONSTRAINT FKh1gwf3xb2p9spjresyursnr
        FOREIGN KEY (rental_id) REFERENCES RENTALS (id);

ALTER TABLE TOKENS
    ADD CONSTRAINT FKh1gwf3xb2p9sp457gm8oycrsr
        FOREIGN KEY (user_id) REFERENCES USERS (id);


