package com.diac.awesomehardwaresupply.priceschedule.factory;

import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import com.diac.awesomehardwaresupply.priceschedule.utility.PricingAdjustments;

import java.util.function.BiFunction;

// TODO: Factory method?
// TODO: Get rid of the static class (violates OCP and creates extra clutter in code)
public class PricingMethodFunctionFactory {

    // TODO: Javadoc
    public BiFunction<ProductDetail, Integer, Integer> pricingMethodFunction(PricingMethod pricingMethod) {
        switch (pricingMethod) {
            case PRICE_OVERRIDE:
                return (productDetail, priceAdjustment) -> priceAdjustment;
            case COST_MARKUP_AMOUNT:
                return (productDetail, priceAdjustment) -> PricingAdjustments.costMarkupAmount(
                        productDetail.getCost(),
                        priceAdjustment
                );
            case COST_MARKUP_PERCENTAGE:
                return (productDetail, priceAdjustment) -> PricingAdjustments.costMarkupPercentage(
                        productDetail.getCost(),
                        priceAdjustment
                );
            case PRICE_DISCOUNT_AMOUNT:
                return (productDetail, priceAdjustment) ->  PricingAdjustments.priceDiscountAmount(
                        productDetail.getListPrice(),
                        priceAdjustment
                );
            case PRICE_DISCOUNT_PERCENTAGE:
                return (productDetail, priceAdjustment) ->  PricingAdjustments.priceDiscountPercentage(
                        productDetail.getListPrice(),
                        priceAdjustment
                );
            default:
                return (productDetail, priceAdjustment) -> productDetail.getListPrice();
        }
    }
}