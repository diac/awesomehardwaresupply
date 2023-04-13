CREATE TABLE user_authority (
    id SERIAL PRIMARY KEY,
    authority VARCHAR NOT NULL,
    auth_user_id INT REFERENCES auth_user(id)
);

COMMENT ON TABLE user_authority IS 'Привилегия пользователя';
COMMENT ON COLUMN user_authority.id IS 'Идентификатор привилегии';
COMMENT ON COLUMN  user_authority.authority IS 'Значение привилегии';
COMMENT ON COLUMN  user_authority.auth_user_id IS 'Идентификатор пользователя (FK)';