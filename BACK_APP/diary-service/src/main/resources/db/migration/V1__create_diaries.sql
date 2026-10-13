CREATE TABLE diaries (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id    BIGINT NOT NULL UNIQUE,
    height     DECIMAL(5, 2) CHECK (height > 0),
    birth_date DATE
);
