package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.dto.ProductPriceRequestDto;
import com.diac.awesomehardwaresupply.domain.dto.ProductPriceResponseDto;
import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.*;
import com.diac.awesomehardwaresupply.priceschedule.repository.*;
import com.diac.awesomehardwaresupply.priceschedule.utility.PricingAdjustments;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис, реализующий логику расчета цен товаров
 */
@Service
@AllArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private static final String PRODUCT_DETAIL_NOT_FOUND_MESSAGE = "Product detail #%s not found";

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

        Optional<PriceModifier> priceModifier = Optional.empty();

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

        if (customerPricing.isPresent()) {
            Optional<PricingStep> pricingStepMatch = customerPricing.get()
                    .getPricingSteps()
                    .stream()
                    .filter(
                            pricingStep -> productPriceRequestDto.getQuantity() >= pricingStep.getMinQuantity()
                                    && productPriceRequestDto.getQuantity() <= pricingStep.getMaxQuantity()
                    ).findFirst();
            priceModifier = pricingStepMatch.map(
                    pricingStep -> new PriceModifier(pricingStep.getPriceAdjustment(), pricingStep.getPricingMethod())
            );
        } else if (productPricing.isPresent()) {
            Optional<PricingStep> pricingStepMatch = productPricing.get()
                    .getPricingSteps()
                    .stream()
                    .filter(
                            pricingStep -> productPriceRequestDto.getQuantity() >= pricingStep.getMinQuantity()
                                    && productPriceRequestDto.getQuantity() <= pricingStep.getMaxQuantity()
                    ).findFirst();
            priceModifier = pricingStepMatch.map(
                    pricingStep -> new PriceModifier(pricingStep.getPriceAdjustment(), pricingStep.getPricingMethod())
            );
        } else if (priceCodePricing.isPresent()) {
            Optional<PricingStep> pricingStepMatch = priceCodePricing.get()
                    .getPricingSteps()
                    .stream()
                    .filter(
                            pricingStep -> productPriceRequestDto.getQuantity() >= pricingStep.getMinQuantity()
                                    && productPriceRequestDto.getQuantity() <= pricingStep.getMaxQuantity()
                    ).findFirst();
            priceModifier = pricingStepMatch.map(
                    pricingStep -> new PriceModifier(pricingStep.getPriceAdjustment(), pricingStep.getPricingMethod())
            );
        }

        return new ProductPriceResponseDto(
                productPriceRequestDto.getProductSku(),
                calculateItemPrice(productDetail, priceModifier)
        );
    }

    private int calculateItemPrice(ProductDetail productDetail, Optional<PriceModifier> priceModifier) {
        return priceModifier.map(
                modifier ->
                        switch (modifier.pricingMethod) {
                            case PRICE_OVERRIDE -> modifier.priceAdjustment;
                            case COST_MARKUP_AMOUNT -> PricingAdjustments.costMarkupAmount(
                                    productDetail.getCost(),
                                    modifier.priceAdjustment
                            );
                            case COST_MARKUP_PERCENTAGE -> PricingAdjustments.costMarkupPercentage(
                                    productDetail.getCost(),
                                    modifier.priceAdjustment
                            );
                            case PRICE_DISCOUNT_AMOUNT -> PricingAdjustments.priceDiscountAmount(
                                    productDetail.getCost(),
                                    modifier.priceAdjustment
                            );
                            case PRICE_DISCOUNT_PERCENTAGE -> PricingAdjustments.priceDiscountPercentage(
                                    productDetail.getCost(),
                                    modifier.priceAdjustment
                            );
                        }
        ).orElse(productDetail.getListPrice());
    }

    private record PriceModifier(int priceAdjustment, PricingMethod pricingMethod) {

    }
}