--liquibase formatted sql

--changeset author:José Luiz Junior:20200413092900
--comment: Create user table
CREATE TABLE users
(
    id NUMERIC(19,0) NOT NULL,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    cpf CHAR(11) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(ID)
);

CREATE SEQUENCE users_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	CACHE 1
	NO CYCLE;

--rollback DROP TABLE users; DROP SEQUENCE users_id_seq