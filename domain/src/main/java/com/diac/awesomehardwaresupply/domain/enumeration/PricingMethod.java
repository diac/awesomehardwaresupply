package com.diac.awesomehardwaresupply.domain.enumeration;

/**
 * Перечисление методов ценообразования
 */
public enum PricingMethod {

    /**
     * Абсолютная величина наценки от стоимости
     */
    COST_MARKUP_AMOUNT,

    /**
     * Процентная величина наценки от стоимости
     */
    COST_MARKUP_PERCENTAGE,

    /**
     * Абсолютная величина скидки от цены
     */
    PRICE_DISCOUNT_AMOUNT,

    /**
     * Процентная величина скидки от цены
     */
    PRICE_DISCOUNT_PERCENTAGE,

    /**
     * Переопределение цены
     */
    PRICE_OVERRIDE,

    /**
     * Списочная цена
     */
    LIST_PRICE
}