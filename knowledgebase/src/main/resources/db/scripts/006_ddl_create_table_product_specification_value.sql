CREATE TABLE product_specification_value (
    id SERIAL PRIMARY KEY,
    value FLOAT,
    product_specification_id INTEGER REFERENCES product_specification(id)
);

COMMENT ON TABLE product_specification_value IS 'Значение спецификации товара';
COMMENT ON COLUMN product_specification_value.id IS 'Идентификатор значения';
COMMENT ON COLUMN product_specification_value.value IS 'Величина значения';
COMMENT ON COLUMN product_specification_value.product_specification_id IS 'Идентификатор спецификации, которой принадлежит значение';