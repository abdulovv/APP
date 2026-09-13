CREATE TABLE diaries (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    height     DECIMAL(5,2),
    birth_date DATE
);