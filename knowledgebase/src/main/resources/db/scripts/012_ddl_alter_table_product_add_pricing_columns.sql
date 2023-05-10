ALTER TABLE
    product
ADD COLUMN
    list_price INTEGER NOT NULL;

ALTER TABLE
    product
ADD COLUMN
    cost INTEGER NOT NULL;

ALTER TABLE
    product
ADD COLUMN
    price_code VARCHAR NOT NULL;

COMMENT ON COLUMN product.list_price IS 'Цена товара по прейскуранту';
COMMENT ON COLUMN product.cost IS 'Стоимость товара';
COMMENT ON COLUMN product.price_code IS 'Код товара';