CREATE TABLE product_specification (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    description VARCHAR NOT NULL,
    units VARCHAR NOT NULL
);

COMMENT ON TABLE product_specification IS 'Спецификация товаров';
COMMENT ON COLUMN product_specification.id IS 'Идентификатор спецификации товаров';
COMMENT ON COLUMN product_specification.name IS 'Наименование спецификации';
COMMENT ON COLUMN product_specification.description IS 'Описание спецификации';
COMMENT ON COLUMN product_specification.units IS 'Единица измерения';