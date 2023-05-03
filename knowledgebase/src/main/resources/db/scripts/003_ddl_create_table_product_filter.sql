CREATE TABLE product_filter (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    description VARCHAR NOT NULL
);

COMMENT ON TABLE product IS 'Фильтр товаров';
COMMENT ON COLUMN product.id IS 'Идентификатор фильтра товаров';
COMMENT ON COLUMN product.name IS 'Наименование фильтра товаров';
COMMENT ON COLUMN product.description IS 'Описание фильтра товаров';