CREATE TABLE notes (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    diary_id   BIGINT NOT NULL REFERENCES diary(id) ON DELETE CASCADE,
    meal_type  VARCHAR(20) CHECK (meal_type IN ('завтрак','полдник','ужин','перекус')),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);