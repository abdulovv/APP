-- Журнал всех выдач и покупок premium. Данные карты и курс копируются на момент операции,
-- чтобы запись не менялась, если карту удалят или курс обновится.
CREATE TABLE premium_transactions (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id         BIGINT           NOT NULL,
    payment_card_id BIGINT,
    card_brand      VARCHAR(20),
    card_last4      VARCHAR(4),
    country_id      BIGINT,
    amount_usd      NUMERIC(10, 2)   NOT NULL CHECK (amount_usd >= 0),
    currency_symbol VARCHAR(10)      NOT NULL,
    exchange_rate   DOUBLE PRECISION NOT NULL CHECK (exchange_rate > 0),
    amount_local    NUMERIC(14, 2)   NOT NULL CHECK (amount_local >= 0),
    period_days     SMALLINT         NOT NULL CHECK (period_days > 0),
    source          VARCHAR(10)      NOT NULL
                    CHECK (source IN ('PURCHASE', 'ADMIN')),
    status          VARCHAR(10)      NOT NULL DEFAULT 'PAID'
                    CHECK (status IN ('PAID', 'FAILED', 'REFUNDED')),
    old_expires_at  TIMESTAMPTZ,
    new_expires_at  TIMESTAMPTZ,
    performed_by    BIGINT,
    created_at      TIMESTAMPTZ      NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_premium_transactions_user
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_premium_transactions_payment_card
        FOREIGN KEY (payment_card_id) REFERENCES payment_cards (id) ON DELETE SET NULL,
    CONSTRAINT fk_premium_transactions_country
        FOREIGN KEY (country_id) REFERENCES countries (id) ON DELETE SET NULL,
    CONSTRAINT fk_premium_transactions_performed_by
        FOREIGN KEY (performed_by) REFERENCES users (id)
);

CREATE INDEX idx_premium_transactions_user_id ON premium_transactions (user_id, created_at DESC);
