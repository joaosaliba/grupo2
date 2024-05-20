CREATE TABLE user_app
(
    id            BIGINT    NOT NULL,
    created_date  TIMESTAMP NOT NULL,
    modified_date TIMESTAMP,
    email         VARCHAR(255),
    password      VARCHAR(255),
    name          VARCHAR(255),
    role          VARCHAR(255),
    CONSTRAINT pk_user_app PRIMARY KEY (id)
);

ALTER TABLE user_app
    ADD CONSTRAINT uc_user_app_email UNIQUE (email);

INSERT INTO user_app (created_date, email, password, name, role)
VALUES ('2024-05-20 10:00:00', 'junior@gmail.com', '$2a$12$Si4jJkaY0DgBIbDKd1nDGuRUB1rM6DHrmkK6u0AsSMAN.yrNuaIRW', 'Junior', 'ADMIN');