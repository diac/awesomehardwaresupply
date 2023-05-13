package com.diac.awesomehardwaresupply.priceschedule.utility;

public final class PricingAdjustments {

    private PricingAdjustments() {
    }

    public static int costMarkupAmount(int cost, int priceAdjustment) {
        return cost + priceAdjustment;
    }

    public static int costMarkupPercentage(int cost, int priceAdjustment) {
        return cost + (cost * priceAdjustment / 100);
    }

    public static int priceDiscountAmount(int price, int priceAdjustment) {
        return price - priceAdjustment;
    }

    public static int priceDiscountPercentage(int price, int priceAdjustment) {
        return price + (price * priceAdjustment / 100);
    }
}