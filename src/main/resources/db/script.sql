DROP DATABASE IF EXISTS ESTATE;

CREATE DATABASE ESTATE;

USE ESTATE;

CREATE TABLE USERS (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
  email VARCHAR(255),
  name VARCHAR(255),
  password VARCHAR(255),
  role VARCHAR(255),
  enabled TINYINT NOT NULL DEFAULT 1,
  created_at timestamp,
  updated_at timestamp default current_timestamp
);

CREATE TABLE RENTALS (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  name VARCHAR(255),
  surface numeric,
  price numeric,
  picture VARCHAR(255),
  description VARCHAR(2000),
  owner_id INT NOT NULL,
  created_at timestamp,
  updated_at timestamp default current_timestamp
);

CREATE TABLE MESSAGES (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  rental_id INT,
  user_id INT,
  message VARCHAR(2000),
  created_at timestamp,
  updated_at timestamp default current_timestamp
);

CREATE UNIQUE INDEX USERS_index ON USERS (email);

ALTER TABLE RENTALS ADD FOREIGN KEY (owner_id) REFERENCES USERS (id);

ALTER TABLE MESSAGES ADD FOREIGN KEY (user_id) REFERENCES USERS (id);

ALTER TABLE MESSAGES ADD FOREIGN KEY (rental_id) REFERENCES RENTALS (id);
