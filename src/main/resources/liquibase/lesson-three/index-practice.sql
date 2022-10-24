-- changeset sbelov:1
CREATE INDEX users_name_index ON users (name);

-- changeset sbelov:2
CREATE INDEX faculty_name_color_index ON users (faculty, color);