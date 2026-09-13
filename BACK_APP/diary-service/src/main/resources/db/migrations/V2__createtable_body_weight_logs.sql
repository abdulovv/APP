CREATE TABLE body_weight_logs (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    diary_id    BIGINT NOT NULL REFERENCES diary(id) ON DELETE CASCADE,
    weight      DECIMAL(5,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);