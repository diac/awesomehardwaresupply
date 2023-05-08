CREATE TABLE product_pricing_pricing_step (
    product_pricing_id INTEGER REFERENCES product_pricing(id),
    pricing_step_id INTEGER REFERENCES pricing_step(id),
    PRIMARY KEY (product_pricing_id, pricing_step_id)
);

COMMENT ON TABLE product_pricing_pricing_step IS 'Связь между правилаими ценообразования товаров и шагами ценообразования';
COMMENT ON COLUMN product_pricing_pricing_step.product_pricing_id IS 'Идентификатор правила ценообразования товаров';
COMMENT ON COLUMN product_pricing_pricing_step.pricing_step_id IS 'Идентификатор шага ценообразования';