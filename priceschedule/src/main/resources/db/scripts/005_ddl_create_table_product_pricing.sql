CREATE TABLE product_pricing (
    id SERIAL PRIMARY KEY,
    product_sku VARCHAR NOT NULL,
    price_level_id INTEGER NOT NULL REFERENCES price_level(id)
);

COMMENT ON TABLE product_pricing IS 'Правило ценообразования товара';
COMMENT ON COLUMN product_pricing.id IS 'Идентификатор правила';
COMMENT ON COLUMN product_pricing.product_sku IS 'Артикул товара';
COMMENT ON COLUMN product_pricing.price_level_id IS 'Идентификатор уровня ценообразования';