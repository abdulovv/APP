CREATE TABLE body_weight_logs (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    diary_id   BIGINT        NOT NULL,
    weight     DECIMAL(5, 2) NOT NULL CHECK (weight > 0),
    created_at TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT fk_body_weight_logs_diary
        FOREIGN KEY (diary_id) REFERENCES diaries (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_body_weight_logs_diary_id_created_at ON body_weight_logs (diary_id, created_at);
