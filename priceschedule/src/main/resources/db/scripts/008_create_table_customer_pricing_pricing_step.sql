CREATE TABLE customer_pricing_pricing_step (
    customer_pricing_id INTEGER REFERENCES customer_pricing(id),
    pricing_step_id INTEGER REFERENCES pricing_step(id),
    PRIMARY KEY (customer_pricing_id, pricing_step_id)
);

COMMENT ON TABLE customer_pricing_pricing_step IS 'Связь между правилами персонального ценообразования и шагами ценообразования';
COMMENT ON COLUMN customer_pricing_pricing_step.customer_pricing_id IS 'Идентификатор правила персонального ценообразования';
COMMENT ON COLUMN customer_pricing_pricing_step.pricing_step_id IS 'Идентификатор шага ценообразования';