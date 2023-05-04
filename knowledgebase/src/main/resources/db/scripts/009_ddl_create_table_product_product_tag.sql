CREATE TABLE product_product_tag (
    product_id INTEGER REFERENCES product(id),
    product_tag_id INTEGER REFERENCES product_tag(id),
    PRIMARY KEY (product_id, product_tag_id)
);

COMMENT ON TABLE product_product_tag IS 'Связь между товарами и тэгами товаров';
COMMENT ON COLUMN product_product_tag.product_id IS 'Идентификатор товара (FK)';
COMMENT ON COLUMN product_product_tag.product_tag_id IS 'Идентификатор тэга товаров (FK)';