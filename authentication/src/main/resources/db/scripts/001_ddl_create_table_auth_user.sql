CREATE TABLE auth_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE auth_user IS 'Пользователь';
COMMENT ON COLUMN auth_user.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN auth_user.username IS 'Имя пользователя';
COMMENT ON COLUMN auth_user.password IS 'Пароль пользоватея';
COMMENT ON COLUMN auth_user.email IS 'Email пользоватея';