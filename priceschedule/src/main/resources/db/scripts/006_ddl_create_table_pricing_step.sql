CREATE TABLE pricing_step (
    id SERIAL PRIMARY KEY,
    pricing_method VARCHAR NOT NULL,
    min_quantity INTEGER NOT NULL,
    max_quantity INTEGER NOT NULL,
    price_adjustment INTEGER NOT NULL
);

COMMENT ON TABLE pricing_step IS 'Шаг ценообразования';
COMMENT ON COLUMN pricing_step.id IS 'Идентификатор шага ценообразования';
COMMENT ON COLUMN pricing_step.pricing_method IS 'Метод ценообразования';
COMMENT ON COLUMN pricing_step.min_quantity IS 'Минимальное количество единиц товара';
COMMENT ON COLUMN pricing_step.max_quantity IS 'Максимальное количество единиц товара';
COMMENT ON COLUMN pricing_step.price_adjustment IS 'Величина изменения цены';