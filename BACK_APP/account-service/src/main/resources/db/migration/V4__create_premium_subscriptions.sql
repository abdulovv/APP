-- Одна строка на пользователя. При истечении строка не удаляется, просто expires_at в прошлом.
CREATE TABLE premium_subscriptions (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id    BIGINT      NOT NULL UNIQUE,
    started_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    expires_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_premium_subscriptions_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT chk_premium_subscriptions_dates CHECK (expires_at > started_at)
);

CREATE INDEX idx_premium_subscriptions_expires_at ON premium_subscriptions (expires_at);
