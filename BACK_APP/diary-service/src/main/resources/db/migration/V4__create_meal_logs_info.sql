CREATE TABLE meal_logs_info (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    meal_log_id  BIGINT         NOT NULL,
    nutrition_id BIGINT         NOT NULL,
    weight       DECIMAL(10, 2) NOT NULL CHECK (weight > 0),
    CONSTRAINT fk_meal_logs_info_meal_log
        FOREIGN KEY (meal_log_id) REFERENCES meal_logs (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_meal_logs_info_meal_log_id ON meal_logs_info (meal_log_id);
