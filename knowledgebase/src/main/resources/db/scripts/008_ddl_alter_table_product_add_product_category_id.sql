ALTER TABLE product
ADD COLUMN product_category_id INTEGER REFERENCES product_category(id);

COMMENT ON COLUMN product.product_category_id IS 'Идентификатор категории товара (FK)';