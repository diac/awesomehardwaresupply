CREATE TABLE price_code_pricing (
    id SERIAL PRIMARY KEY,
    price_level_id INTEGER NOT NULL REFERENCES price_level(id),
    price_code_id INTEGER NOT NULL REFERENCES price_code(id)
);

COMMENT ON TABLE price_code_pricing IS 'Правило ценообразования по коду цены';
COMMENT ON COLUMN price_code_pricing.id IS 'Уровень цены';
COMMENT ON COLUMN price_code_pricing.price_level_id IS 'Идентификатор уровня цены';
COMMENT ON COLUMN price_code_pricing.price_code_id IS 'Идентификатор кода цены';