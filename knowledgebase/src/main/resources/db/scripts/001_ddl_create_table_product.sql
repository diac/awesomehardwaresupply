CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    description VARCHAR NOT NULL,
    sku VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE product IS 'Товар';
COMMENT ON COLUMN product.id IS 'Идентификатор товара';
COMMENT ON COLUMN product.name IS 'Наименование товара';
COMMENT ON COLUMN product.description IS 'Описание товара';
COMMENT ON COLUMN product.sku IS 'Артикул товара';