CREATE TABLE users (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    firstname    VARCHAR(50)  NOT NULL,
    lastname     VARCHAR(50)  NOT NULL,
    email        VARCHAR(254) NOT NULL UNIQUE,
    phone_number VARCHAR(16)  NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL,
    birth_date   DATE         NOT NULL,
    country_id   BIGINT       NOT NULL,
    is_premium   BOOLEAN      NOT NULL DEFAULT FALSE,
    role         VARCHAR(10)  NOT NULL DEFAULT 'USER' CHECK (role IN ('USER', 'ADMIN')),
    CONSTRAINT fk_users_country
        FOREIGN KEY (country_id) REFERENCES countries (id)
);

CREATE INDEX idx_users_country_id ON users (country_id);