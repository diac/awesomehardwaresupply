CREATE TABLE product_product_specification_value (
    product_id INTEGER REFERENCES product(id),
    product_specification_value_id INTEGER REFERENCES product_specification_value(id),
    PRIMARY KEY (product_id, product_specification_value_id)
);

COMMENT ON TABLE product_product_specification_value IS 'Связь между товарами и значениями спецификаций товаров';
COMMENT ON COLUMN product_product_specification_value.product_id IS 'Идентификатор товара (FK)';
COMMENT ON COLUMN product_product_specification_value.product_specification_value_id IS 'Идентификатор значения спецификации товара (FK)';