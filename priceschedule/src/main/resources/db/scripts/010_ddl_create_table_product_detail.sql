CREATE TABLE product_detail (
    id SERIAL PRIMARY KEY,
    product_sku VARCHAR UNIQUE NOT NULL,
    list_price INTEGER NOT NULL,
    cost INTEGER NOT NULL,
    price_code VARCHAR NOT NULL
);

COMMENT ON TABLE product_detail IS 'Подробности товара';
COMMENT ON COLUMN product_detail.id IS 'Идентификатор подробностей товара';
COMMENT ON COLUMN product_detail.product_sku IS 'Артикул товара';
COMMENT ON COLUMN product_detail.list_price IS 'Цена товара по прейскуранту';
COMMENT ON COLUMN product_detail.cost IS 'Стоимость товара';
COMMENT ON COLUMN product_detail.price_code IS 'Код цены';