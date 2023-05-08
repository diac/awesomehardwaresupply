package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

import java.util.List;

/**
 * Модель данных "Правило ценообразования по коду цены"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PriceCodePricing {

    /**
     * Идентификатор правила
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Уровень цены
     */
    private PriceLevel priceLevel;

    /**
     * Код цены
     */
    private PriceCode priceCode;

    /**
     * Шаги ценообразования
     */
    List<PricingStep> pricingSteps;
}