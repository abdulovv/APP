CREATE TABLE meal_logs (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    diary_id   BIGINT      NOT NULL,
    meal_type  VARCHAR(20) NOT NULL
               CONSTRAINT chk_meal_logs_meal_type
               CHECK (meal_type IN ('BREAKFAST', 'LUNCH', 'DINNER', 'SNACK')),
    eaten_at   TIMESTAMP   NOT NULL DEFAULT now(),
    created_at TIMESTAMP   NOT NULL DEFAULT now(),
    CONSTRAINT fk_meal_logs_diary
        FOREIGN KEY (diary_id) REFERENCES diaries (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_meal_logs_diary_id_eaten_at ON meal_logs (diary_id, eaten_at);
