CREATE TABLE nutrition_nutrients (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nutrition_id BIGINT         NOT NULL,
    nutrient_id  BIGINT         NOT NULL,
    value        DECIMAL(10, 2) NOT NULL CHECK (value >= 0),
    CONSTRAINT fk_nn_nutrition
        FOREIGN KEY (nutrition_id) REFERENCES nutrition (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_nn_nutrient
        FOREIGN KEY (nutrient_id) REFERENCES nutrients (id)
        ON DELETE CASCADE,
    CONSTRAINT uq_nutrition_nutrient UNIQUE (nutrition_id, nutrient_id)
);

CREATE INDEX idx_nn_nutrient_id ON nutrition_nutrients (nutrient_id);

INSERT INTO nutrition_nutrients (nutrition_id, nutrient_id, value) VALUES
(1, 1, 0.30),   (1, 2, 0.20),   (1, 3, 14.00),  (1, 4, 2.40),   (1, 5, 10.00),
(2, 1, 1.10),   (2, 2, 0.30),   (2, 3, 23.00),  (2, 4, 2.60),   (2, 5, 12.00),
(3, 1, 0.90),   (3, 2, 0.10),   (3, 3, 12.00),  (3, 4, 2.40),   (3, 5, 9.00),
(4, 1, 0.40),   (4, 2, 0.10),   (4, 3, 15.00),  (4, 4, 3.10),   (4, 5, 10.00),
(5, 1, 0.90),   (5, 2, 0.20),   (5, 3, 10.00),  (5, 4, 2.80),   (5, 5, 4.70),
(6, 1, 0.70),   (6, 2, 0.10),   (6, 3, 3.60),   (6, 4, 0.50),   (6, 5, 1.70),
(7, 1, 0.90),   (7, 2, 0.20),   (7, 3, 3.90),   (7, 4, 1.20),   (7, 5, 2.60),
(8, 1, 3.40),   (8, 2, 3.20),   (8, 3, 4.80),   (8, 4, 0.00),   (8, 5, 4.80),
(9, 1, 18.00),  (9, 2, 5.00),   (9, 3, 3.30),   (9, 4, 0.00),   (9, 5, 3.30),
(10, 1, 5.00),  (10, 2, 3.00),  (10, 3, 7.50),  (10, 4, 0.00),  (10, 5, 7.00),
(11, 1, 23.00), (11, 2, 1.90),  (11, 3, 0.00),  (11, 4, 0.00),  (11, 5, 0.00),
(12, 1, 26.00), (12, 2, 15.00), (12, 3, 0.00),  (12, 4, 0.00),  (12, 5, 0.00),
(13, 1, 20.00), (13, 2, 13.00), (13, 3, 0.00),  (13, 4, 0.00),  (13, 5, 0.00),
(14, 1, 24.00), (14, 2, 0.30),  (14, 3, 0.20),  (14, 4, 0.00),  (14, 5, 0.00),
(15, 1, 2.70),  (15, 2, 0.30),  (15, 3, 28.00), (15, 4, 0.40),  (15, 5, 0.10),
(16, 1, 4.50),  (16, 2, 1.10),  (16, 3, 25.00), (16, 4, 2.70),  (16, 5, 0.00),
(17, 1, 2.50),  (17, 2, 1.40),  (17, 3, 12.00), (17, 4, 1.70),  (17, 5, 0.50),
(18, 1, 9.00),  (18, 2, 0.40),  (18, 3, 20.00), (18, 4, 7.90),  (18, 5, 1.80),
(19, 1, 21.00), (19, 2, 50.00), (19, 3, 21.67), (19, 4, 12.33), (19, 5, 5.00),
(20, 1, 15.33), (20, 2, 65.00), (20, 3, 13.33), (20, 4, 6.67),  (20, 5, 2.33),
(21, 1, 8.33),  (21, 2, 3.00),  (21, 3, 50.00), (21, 4, 2.33),  (21, 5, 4.00),
(22, 1, 7.60),  (22, 2, 34.00), (22, 3, 56.00), (22, 4, 2.40),  (22, 5, 54.00),
(27, 1, 0.70),  (27, 2, 0.10),  (27, 3, 10.50), (27, 4, 0.20),  (27, 5, 8.50),
(28, 1, 0.00),  (28, 2, 0.00),  (28, 3, 0.00),  (28, 4, 0.00),  (28, 5, 0.00),
(29, 1, 0.20),  (29, 2, 0.00),  (29, 3, 0.00),  (29, 4, 0.00),  (29, 5, 0.00),
(30, 1, 3.00),  (30, 2, 0.67),  (30, 3, 73.33), (30, 4, 3.67),  (30, 5, 60.00);

INSERT INTO nutrition_nutrients (nutrition_id, nutrient_id, value) VALUES
(23, 6, 2.00),   (23, 7, 1.00),
(24, 6, 5.00),   (24, 7, 2.00),
(25, 6, 15.00),  (25, 7, 5.00),
(26, 6, 105.00), (26, 7, 600.00);

INSERT INTO nutrition_nutrients (nutrition_id, nutrient_id, value)
SELECT n.id,
       (SELECT id FROM nutrients WHERE name = 'Калорийность'),
       ROUND(4 * COALESCE(p.value, 0) + 9 * COALESCE(f.value, 0) + 4 * COALESCE(c.value, 0), 2)
FROM nutrition n
LEFT JOIN nutrition_nutrients p
       ON p.nutrition_id = n.id AND p.nutrient_id = (SELECT id FROM nutrients WHERE name = 'Белки')
LEFT JOIN nutrition_nutrients f
       ON f.nutrition_id = n.id AND f.nutrient_id = (SELECT id FROM nutrients WHERE name = 'Жиры')
LEFT JOIN nutrition_nutrients c
       ON c.nutrition_id = n.id AND c.nutrient_id = (SELECT id FROM nutrients WHERE name = 'Углеводы');
