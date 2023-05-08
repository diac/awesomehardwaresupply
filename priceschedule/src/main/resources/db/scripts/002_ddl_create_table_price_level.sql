CREATE TABLE price_level (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NUll
);

COMMENT ON TABLE price_level IS 'Уровень цены';
COMMENT ON COLUMN price_level.id IS 'Идентификатор уровня цены';
COMMENT ON COLUMN price_level.name IS 'Имя уровня цены';