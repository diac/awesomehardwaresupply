package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

import java.util.List;

/**
 * Модель данных "Правило персонального ценообразования товара"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class CustomerPricing {

    /**
     * Идентификатор правила
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Персональный номер клиента
     */
    private String customerNumber;

    /**
     * Артикул товара
     */
    private String productSku;

    /**
     * Шаги ценообразования
     */
    private List<PricingStep> pricingSteps;
}