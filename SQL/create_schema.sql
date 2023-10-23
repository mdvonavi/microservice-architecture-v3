CREATE DATABASE microservice;

\c microservice

CREATE SCHEMA dev;

CREATE TABLE dev.hard_skills
(
    id SERIAL,
    name varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE dev.cities
(
    id SERIAL,
    name varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE dev.images
(
    id varchar(10),
    link varchar(2048) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE dev.users
(
    id bigserial,
	first_name varchar(30),
	middle_name varchar(30),
	last_name varchar(30),
	sex integer,
	birth_date date,
	city int references dev.cities(id),
	avatar varchar(10) references dev.images(id),
	info varchar(256),
	nickname varchar(20) NOT NULL,
	email varchar(256),
    phone varchar(11),
    PRIMARY KEY (id)
);

CREATE TABLE dev.users_hard_skills
(
	id integer,
	hard_skill int references dev.hard_skills(id) NOT NULL,
	user_id bigserial references dev.users(id) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE dev.subscriptions
(
	id SERIAL,
	user_id bigserial references dev.users(id) NOT NULL,
	subs_id bigserial references dev.users(id) NOT NULL,
	PRIMARY KEY (id)
);
