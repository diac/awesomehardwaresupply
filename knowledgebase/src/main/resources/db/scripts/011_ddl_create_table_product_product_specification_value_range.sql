CREATE TABLE product_product_specification_value_range (
    product_id INTEGER REFERENCES product(id),
    product_specification_value_range_id INTEGER REFERENCES product_specification_value_range(id),
    PRIMARY KEY (product_id, product_specification_value_range_id)
);

COMMENT ON TABLE product_product_specification_value_range IS 'Связь между товарами и диапазонами значений спецификаций';
COMMENT ON COLUMN product_product_specification_value_range.product_id IS 'Идентификатор товара (FK)';
COMMENT ON COLUMN product_product_specification_value_range.product_specification_value_range_id IS 'Идентификатор диапазона значений спецификации';