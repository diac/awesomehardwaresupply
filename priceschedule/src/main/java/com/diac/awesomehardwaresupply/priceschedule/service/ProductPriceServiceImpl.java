package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.dto.ProductPriceRequestDto;
import com.diac.awesomehardwaresupply.domain.dto.ProductPriceResponseDto;
import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.model.*;
import com.diac.awesomehardwaresupply.priceschedule.factory.PricingMethodFunctionFactory;
import com.diac.awesomehardwaresupply.priceschedule.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

/**
 * Сервис, реализующий логику расчета цен товаров
 */
@Service
@AllArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    /**
     * Метод ценообразования по умолчанию
     */
    private static final PricingMethod DEFAULT_PRICING_METHOD = PricingMethod.LIST_PRICE;

    /**
     * Величина корректировки цены по умолчанию
     */
    private static final int DEFAULT_PRICE_ADJUSTMENT = 0;

    /**
     * Сервис для работы с объектами модели ProductDetail
     */
    private final ProductDetailService productDetailService;

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
        ProductDetail productDetail = productDetailService.findByProductSku(productPriceRequestDto.getProductSku());
        PricingStep pricingStepMatch = getPricingStepMatch(productPriceRequestDto);
        return new ProductPriceResponseDto(
                productPriceRequestDto.getProductSku(),
                calculateItemPrice(
                        productDetail,
                        pricingStepMatch.getPricingMethod(),
                        pricingStepMatch.getPriceAdjustment()
                )
        );
    }

    /**
     * Найти подходящий шаг ценообразования по переданному ProductPriceRequestDto
     *
     * @param productPriceRequestDto Объект ProductPriceRequestDto
     * @return Шаг ценообразования
     */
    private PricingStep getPricingStepMatch(ProductPriceRequestDto productPriceRequestDto) {
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
        return customerPricing.map(
                pricingStepFilter(productPriceRequestDto.getQuantity())
        ).orElseGet(
                () -> productPricing.map(
                        pricingStepFilter(productPriceRequestDto.getQuantity())
                ).orElseGet(
                        () -> priceCodePricing.flatMap(
                                pricingStepFilter(productPriceRequestDto.getQuantity())
                        )
                )
        ).orElse(
                PricingStep.builder()
                        .pricingMethod(DEFAULT_PRICING_METHOD)
                        .priceAdjustment(DEFAULT_PRICE_ADJUSTMENT)
                        .build()
        );
    }

    /**
     * Функция-фильтр шагов ценообразования
     *
     * @param quantity Количество товаров в позиции
     * @return Функция Function<Pricing, Optional<PricingStep>>, где Pricing -- правило ценообразования,
     * а Optional<PricingStep> -- Optional с подходящим шагом ценообразования
     */
    private Function<Pricing, Optional<PricingStep>> pricingStepFilter(int quantity) {
        return (pricing) -> pricing.getPricingSteps()
                .stream()
                .filter(
                        pricingStep ->
                                quantity >= pricingStep.getMinQuantity()
                                        && quantity <= pricingStep.getMaxQuantity()
                )
                .findFirst();
    }

    /**
     * Рассчитать цену товара в позиции
     *
     * @param productDetail   Подробности товара
     * @param pricingMethod   Метод ценообразования
     * @param priceAdjustment Величина корректировки цены
     * @return Рассчитанная цена товара
     */
    private int calculateItemPrice(ProductDetail productDetail, PricingMethod pricingMethod, int priceAdjustment) {
        PricingMethodFunctionFactory pricingMethodFunctionFactory = new PricingMethodFunctionFactory();
        return pricingMethodFunctionFactory.pricingMethodFunction(pricingMethod)
                .apply(productDetail, priceAdjustment);
    }
}