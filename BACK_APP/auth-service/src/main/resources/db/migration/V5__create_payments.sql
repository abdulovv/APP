CREATE TABLE payments (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id     BIGINT      NOT NULL,
    holder_name VARCHAR(100) NOT NULL,
    brand       VARCHAR(20)  NOT NULL,
    last4       VARCHAR(4)      NOT NULL CHECK (last4 ~ '^[0-9]{4}$'),
    exp_month   SMALLINT     NOT NULL CHECK (exp_month BETWEEN 1 AND 12),
    exp_year    SMALLINT     NOT NULL CHECK (exp_year >= 2000),
    is_default  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_payments_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uq_payments_card
        UNIQUE (user_id, brand, last4, exp_month, exp_year)
);

CREATE INDEX idx_payments_user_id ON payments (user_id);

-- Не больше одной карты «по умолчанию» на пользователя
CREATE UNIQUE INDEX uq_payments_default_per_user
    ON payments (user_id) WHERE is_default;
