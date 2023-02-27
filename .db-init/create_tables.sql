--CREATE DATABASE person_db;

CREATE TABLE people (
    person_id serial PRIMARY KEY,
	name VARCHAR ( 50 ) UNIQUE NOT NULL,
	age int
);