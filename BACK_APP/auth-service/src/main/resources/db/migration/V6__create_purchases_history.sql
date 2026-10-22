CREATE TABLE purchases_history (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id         BIGINT           NOT NULL,
    payment_id      BIGINT,
    card_brand      VARCHAR(20),
    card_last4      VARCHAR(4),
    country_id      BIGINT,
    amount_usd      NUMERIC(10, 2)   NOT NULL CHECK (amount_usd >= 0),
    currency_symbol VARCHAR(10)      NOT NULL,
    exchange_rate   DOUBLE PRECISION NOT NULL CHECK (exchange_rate > 0),
    amount_local    NUMERIC(14, 2)   NOT NULL CHECK (amount_local >= 0),
    period_days     INT              NOT NULL CHECK (period_days > 0),
    source          VARCHAR(10)      NOT NULL
                    CHECK (source IN ('PURCHASE', 'ADMIN')),
    status          VARCHAR(10)      NOT NULL DEFAULT 'PAID'
                    CHECK (status IN ('PAID', 'FAILED', 'REFUNDED')),
    old_expires_at  TIMESTAMPTZ,
    new_expires_at  TIMESTAMPTZ,
    performed_by    BIGINT,
    created_at      TIMESTAMPTZ      NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_purchases_user
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_purchases_payment
        FOREIGN KEY (payment_id) REFERENCES payments (id) ON DELETE SET NULL,
    CONSTRAINT fk_purchases_country
        FOREIGN KEY (country_id) REFERENCES countries (id) ON DELETE SET NULL,
    CONSTRAINT fk_purchases_admin
        FOREIGN KEY (performed_by) REFERENCES users (id)
);

CREATE INDEX idx_purchases_user_id ON purchases_history (user_id, created_at DESC);
