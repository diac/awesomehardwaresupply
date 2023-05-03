CREATE TABLE product_tag (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    description VARCHAR NOT NULL,
    product_filter_id INTEGER REFERENCES product_filter(id)
);

COMMENT ON TABLE product_tag IS 'Тэг товаров';
COMMENT ON COLUMN product_tag.id IS 'Идентификатор тэга';
COMMENT ON COLUMN product_tag.name IS 'Наименование тэга';
COMMENT ON COLUMN product_tag.description IS 'Описание тэга';
COMMENT ON COLUMN product_tag.product_filter_id IS 'Идентификатор фильтра, к которому принадлежит тэг';