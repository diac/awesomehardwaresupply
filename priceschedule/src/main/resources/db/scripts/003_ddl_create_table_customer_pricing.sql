CREATE TABLE customer_pricing (
    id SERIAL PRIMARY KEY,
    customer_number VARCHAR NOT NULL,
    product_sku VARCHAR NOT NULL,
    UNIQUE (customer_number, product_sku)
);

COMMENT ON TABLE customer_pricing IS 'Правило персонального ценообразования товара';
COMMENT ON COLUMN customer_pricing.id IS 'Идентификатор правила';
COMMENT ON COLUMN customer_pricing.customer_number IS 'Персональный номер клиента';
COMMENT ON COLUMN customer_pricing.product_sku IS 'Артикул товара';