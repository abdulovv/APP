-- Дата отмены подписки. NULL — подписка не отменена.
-- После отмены премиум действует до expires_at, просто не продлевается.
ALTER TABLE premium_subscriptions ADD COLUMN cancelled_at TIMESTAMPTZ NULL;
