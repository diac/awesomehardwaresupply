CREATE TABLE price_code (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

COMMENT ON TABLE price_code IS 'Код цены';
COMMENT ON COLUMN price_code.id IS 'Идентификатор кода цены';
COMMENT ON COLUMN price_code.name IS 'Имя кода цены';