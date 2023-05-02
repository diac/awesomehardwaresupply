CREATE TABLE product_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    description VARCHAR NOT NULL,
    parent_category_id INTEGER
);

COMMENT ON TABLE product_category IS 'Категория товаров';
COMMENT ON COLUMN  product_category.id IS 'Идентификатор категории товаров';
COMMENT ON COLUMN product_category.name IS 'Наименование категории товаров';
COMMENT ON COLUMN product_category.description IS 'Описание категории товаров';
COMMENT ON COLUMN product_category.parent_category_id IS 'Идентификатор родительской категории';