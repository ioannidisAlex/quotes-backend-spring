CREATE DATABASE IF NOT EXISTS `@localhost`;

USE `@localhost`;

CREATE SCHEMA quotes;

USE quotes;

CREATE TABLE quote (
    id INT PRIMARY KEY AUTO_INCREMENT,
    author VARCHAR(255) NULL,
    text TEXT NOT NULL
);