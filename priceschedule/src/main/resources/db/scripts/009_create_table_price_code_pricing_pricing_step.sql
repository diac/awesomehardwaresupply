CREATE TABLE price_code_pricing_pricing_step (
    price_code_pricing_id INTEGER REFERENCES price_code_pricing(id),
    pricing_step_id INTEGER REFERENCES pricing_step(id),
    PRIMARY KEY(price_code_pricing_id, pricing_step_id)
);

COMMENT ON TABLE price_code_pricing_pricing_step IS 'Связь между правилами ценообразования по коду цены и шагами ценообразования';
COMMENT ON COLUMN price_code_pricing_pricing_step.price_code_pricing_id IS 'Идентификатор правила ценообразования по коду цены';
COMMENT ON COLUMN price_code_pricing_pricing_step.pricing_step_id IS 'Идентификатор шага ценообразования';