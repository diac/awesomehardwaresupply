package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

import java.util.List;

/**
 * Модель данных "Правило ценообразования товара"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductPricing {

    /**
     * Идентификатор правила
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Артикул товара
     */
    private String productSku;

    /**
     * Уровень ценообразования
     */
    private PriceLevel priceLevel;

    /**
     * Шаги ценообразования
     */
    private List<PricingStep> pricingSteps;
}