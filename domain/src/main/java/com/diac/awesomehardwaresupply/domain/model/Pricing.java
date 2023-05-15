package com.diac.awesomehardwaresupply.domain.model;

import java.util.List;

/**
 * Обобщающий интерфейс для моделей "Правило ценообразования"
 */
public interface Pricing {

    List<PricingStep> getPricingSteps();
}