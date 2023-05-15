package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.dto.ProductPriceRequestDto;
import com.diac.awesomehardwaresupply.domain.dto.ProductPriceResponseDto;
import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.*;
import com.diac.awesomehardwaresupply.priceschedule.factory.PricingMethodFunctionFactory;
import com.diac.awesomehardwaresupply.priceschedule.repository.*;
import com.diac.awesomehardwaresupply.priceschedule.utility.PricingAdjustments;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Сервис, реализующий логику расчета цен товаров
 */
@Service
@AllArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {


    // TODO: Use service instead?
    private static final String PRODUCT_DETAIL_NOT_FOUND_MESSAGE = "Product detail #%s not found";

    /**
     * Метод ценообразования по умолчанию
     */
    private static final PricingMethod DEFAULT_PRICING_METHOD = PricingMethod.LIST_PRICE;

    /**
     * Величина корректировки цены по умолчанию
     */
    private static final int DEFAULT_PRICE_ADJUSTMENT = 0;

    /**
     * Репозиторий для хранения объектов CustomerPricing
     */
    private final CustomerPricingRepository customerPricingRepository;

    /**
     * Репозиторий для хранения объектов PriceCodePricing
     */
    private final PriceCodePricingRepository priceCodePricingRepository;

    /**
     * Репозиторий для хранения объектов ProductPricing
     */
    private final ProductPricingRepository productPricingRepository;

    /**
     * Репозиторий для хранения объектов ProductDetail
     */
    private final ProductDetailRepository productDetailRepository;

    /**
     * Репозиторий для хранения объектов PriceLevel
     */
    private final PriceLevelRepository priceLevelRepository;

    /**
     * Репозиторий для хранения объектов PriceCode
     */
    private final PriceCodeRepository priceCodeRepository;

    /**
     * Рассчитать цену товара
     *
     * @param productPriceRequestDto Объект-запрос цены
     * @return Объект ProductPriceResponseDto с рассчитанной ценой
     */
    @Override
    public ProductPriceResponseDto calculate(ProductPriceRequestDto productPriceRequestDto) {
        ProductDetail productDetail = productDetailRepository.findByProductSku(productPriceRequestDto.getProductSku())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format(PRODUCT_DETAIL_NOT_FOUND_MESSAGE, productPriceRequestDto.getProductSku())
                        )
                );
        Optional<CustomerPricing> customerPricing = customerPricingRepository.findByCustomerNumberAndProductSku(
                productPriceRequestDto.getCustomerNumber(),
                productPriceRequestDto.getProductSku()
        );
        Optional<ProductPricing> productPricing = productPricingRepository.findByProductSkuAndPriceLevel(
                productPriceRequestDto.getProductSku(),
                priceLevelRepository.findByName(productPriceRequestDto.getPriceLevel()).orElse(new PriceLevel())
        );
        Optional<PriceCodePricing> priceCodePricing = priceCodePricingRepository.findByPriceLevelAndPriceCode(
                priceLevelRepository.findByName(productPriceRequestDto.getPriceLevel()).orElse(new PriceLevel()),
                priceCodeRepository.findByName(productPriceRequestDto.getPriceCode()).orElse(new PriceCode())
        );
        PricingStep pricingStepMatch = customerPricing.map(
                pricing -> pricing.getPricingSteps()
                        .stream()
                        .filter(
                                pricingStep -> productPriceRequestDto.getQuantity() >= pricingStep.getMinQuantity()
                                        && productPriceRequestDto.getQuantity() <= pricingStep.getMaxQuantity()
                        )
                        .findFirst()
        ).orElseGet(
                () -> productPricing.map(
                        pricing -> pricing.getPricingSteps()
                                .stream()
                                .filter(
                                        pricingStep -> productPriceRequestDto.getQuantity() >= pricingStep.getMinQuantity()
                                                && productPriceRequestDto.getQuantity() <= pricingStep.getMaxQuantity()
                                )
                                .findFirst()
                ).orElseGet(
                        () -> priceCodePricing.map(
                                pricing -> pricing.getPricingSteps()
                                        .stream()
                                        .filter(
                                                pricingStep -> productPriceRequestDto.getQuantity() >= pricingStep.getMinQuantity()
                                                        && productPriceRequestDto.getQuantity() <= pricingStep.getMaxQuantity()
                                        )
                                        .findFirst()
                        ).orElse(Optional.empty())
                )
        ).orElse(
                PricingStep.builder()
                        .pricingMethod(DEFAULT_PRICING_METHOD)
                        .priceAdjustment(DEFAULT_PRICE_ADJUSTMENT)
                        .build()
        );
        return new ProductPriceResponseDto(
                productPriceRequestDto.getProductSku(),
                calculateItemPrice(
                        productDetail,
                        pricingStepMatch.getPricingMethod(),
                        pricingStepMatch.getPriceAdjustment()
                )
        );
    }

    private int calculateItemPrice(ProductDetail productDetail, PricingMethod pricingMethod, int priceAdjustment) {
        PricingMethodFunctionFactory pricingMethodFunctionFactory = new PricingMethodFunctionFactory();
        return pricingMethodFunctionFactory.pricingMethodFunction(pricingMethod)
                .apply(productDetail, priceAdjustment);
    }
}