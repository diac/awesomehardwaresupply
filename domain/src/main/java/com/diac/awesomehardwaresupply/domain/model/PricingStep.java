package com.diac.awesomehardwaresupply.domain.model;

import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import lombok.*;

/**
 * Модель данных "Шаг ценообразования"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PricingStep {

    /**
     * Идентификатор шага ценообразования
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Метод ценообразования
     */
    private PricingMethod pricingMethod;

    /**
     * Минимальное количество единиц товара
     */
    private int minQuantity;

    /**
     * Максимальное количество единиц товара
     */
    private int maxQuantity;

    /**
     * Величина изменения цены
     */
    private int priceAdjustment;
}