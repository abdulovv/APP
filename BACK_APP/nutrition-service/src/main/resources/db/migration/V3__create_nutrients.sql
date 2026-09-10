CREATE TABLE nutrients (
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unit VARCHAR(50)
);

INSERT INTO nutrients (name, unit) VALUES
('Белки', 'г'),
('Жиры', 'г'),
('Углеводы', 'г'),
('Клетчатка', 'г'),
('Сахар', 'г'),
('Натрий', 'мг'),
('Калий', 'мг'),
('Кальций', 'мг'),
('Железо', 'мг'),
('Витамин C', 'мг');