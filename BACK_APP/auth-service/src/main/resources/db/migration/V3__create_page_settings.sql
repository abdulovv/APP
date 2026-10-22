CREATE TABLE page_settings (
    page_key  VARCHAR(50)  PRIMARY KEY,
    is_active BOOLEAN      NOT NULL DEFAULT TRUE,
    reason    VARCHAR(255)
);

INSERT INTO page_settings (page_key) VALUES
('plan'),
('products'),
('statistics'),
('diary'),
('profile');