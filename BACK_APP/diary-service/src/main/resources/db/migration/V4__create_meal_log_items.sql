-- Продукты внутри приёма пищи. product_id ссылается на продукт в другой базе (product-service),
-- поэтому внешнего ключа на него нет.
CREATE TABLE meal_log_items (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    meal_log_id BIGINT         NOT NULL,
    product_id  BIGINT         NOT NULL,
    weight      DECIMAL(10, 2) NOT NULL CHECK (weight > 0),
    CONSTRAINT fk_meal_log_items_meal_log
        FOREIGN KEY (meal_log_id) REFERENCES meal_logs (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_meal_log_items_meal_log_id ON meal_log_items (meal_log_id);
