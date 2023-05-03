CREATE TABLE product_specification_value_range (
    id SERIAL PRIMARY KEY,
    value_min FLOAT NOT NULL,
    value_max FLOAT NOT NULL,
    product_specification_id INTEGER REFERENCES product_specification(id)
);

COMMENT ON TABLE product_specification_value_range IS 'Диапазон значений спецификации товара';
COMMENT ON COLUMN product_specification_value_range.id IS 'Идентификатор диапазона значений';
COMMENT ON COLUMN product_specification_value_range.value_min IS 'Минимальная величина значения';
COMMENT ON COLUMN product_specification_value_range.value_max IS 'Максимальная величина значения';
COMMENT ON COLUMN product_specification_value_range.product_specification_id IS 'Идентификатор спецификации, которой принадлежит диапазон значений';