ALTER TABLE students
    ADD CHECK (age >= 16);
ALTER TABLE students
    ALTER COLUMN name SET NOT NULL;
ALTER TABLE students
    ADD CONSTRAINT unique_name UNIQUE (name);
ALTER TABLE students
    ALTER COLUMN age SET DEFAULT 20;
