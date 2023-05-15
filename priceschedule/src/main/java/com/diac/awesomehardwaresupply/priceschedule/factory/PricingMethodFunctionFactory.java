package com.diac.awesomehardwaresupply.priceschedule.factory;

import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import lombok.Getter;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * Фабрика, производящая функции расчета цен для различных методов ценообразования
 */
@Getter
public class PricingMethodFunctionFactory {

    /**
     * Функция переопределения цены
     */
    private static final BiFunction<ProductDetail, Integer, Integer> PRICE_OVERRIDE_FUNCTION
            = (productDetail, priceAdjustment) -> priceAdjustment;

    /**
     * Функция наценки от величины стоимости товара на абсолютную величину
     */
    private static final BiFunction<ProductDetail, Integer, Integer> COST_MARKUP_AMOUNT_FUNCTION
            = (productDetail, priceAdjustment) -> productDetail.getCost() + priceAdjustment;

    /**
     * Функция наценки от величины стоимости товара на процентную величину
     */
    private static final BiFunction<ProductDetail, Integer, Integer> COST_AMOUNT_PERCENTAGE_FUNCTION
            = (productDetail, priceAdjustment) -> productDetail.getCost()
            + (productDetail.getCost() * priceAdjustment / 100);

    /**
     * Функция скидки от цены товара на абсолютную величину
     */
    private static final BiFunction<ProductDetail, Integer, Integer> PRICE_DISCOUNT_AMOUNT_FUNCTION
            = (productDetail, priceAdjustment) -> productDetail.getListPrice() - priceAdjustment;

    /**
     * Функция скидки от цены товара на процентную величину
     */
    private static final BiFunction<ProductDetail, Integer, Integer> PRICE_DISCOUNT_PERCENTAGE_FUNCTION
            = (productDetail, priceAdjustment) -> productDetail.getListPrice() + (
            productDetail.getListPrice() * priceAdjustment / 100);

    /**
     * Функция, возвращающая списочную цену товара
     */
    private static final BiFunction<ProductDetail, Integer, Integer> LIST_PRICE_FUNCTION
            = (productDetail, priceAdjustment) -> productDetail.getListPrice();

    /**
     * Карта соответствий методов ценообразования и соответствующих функций вычисления цены
     */
    private static final Map<PricingMethod, BiFunction<ProductDetail, Integer, Integer>> PRICING_METHOD_FUNCTIONS_MAP
            = Map.of(
            PricingMethod.PRICE_OVERRIDE, PRICE_OVERRIDE_FUNCTION,
            PricingMethod.COST_MARKUP_AMOUNT, COST_MARKUP_AMOUNT_FUNCTION,
            PricingMethod.COST_MARKUP_PERCENTAGE, COST_AMOUNT_PERCENTAGE_FUNCTION,
            PricingMethod.PRICE_DISCOUNT_AMOUNT, PRICE_DISCOUNT_AMOUNT_FUNCTION,
            PricingMethod.PRICE_DISCOUNT_PERCENTAGE, PRICE_DISCOUNT_PERCENTAGE_FUNCTION,
            PricingMethod.LIST_PRICE, LIST_PRICE_FUNCTION
    );

    /**
     * Функция вычисления цены
     */
    private final BiFunction<ProductDetail, Integer, Integer> pricingMethodFunction;

    /**
     * Метод ценообразования
     */
    private final PricingMethod pricingMethod;

    public PricingMethodFunctionFactory(PricingMethod pricingMethod) {
        this.pricingMethod = pricingMethod;
        this.pricingMethodFunction = PRICING_METHOD_FUNCTIONS_MAP.get(pricingMethod);
    }
}