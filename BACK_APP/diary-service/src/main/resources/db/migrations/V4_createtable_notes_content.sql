CREATE TABLE notes_content (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    note_id      BIGINT NOT NULL REFERENCES note(id) ON DELETE CASCADE,
    nutrition_id BIGINT NOT NULL,
    weight       DECIMAL(10,2) NOT NULL
);