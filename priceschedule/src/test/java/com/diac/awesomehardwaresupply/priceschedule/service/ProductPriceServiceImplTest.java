package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.dto.ProductPriceRequestDto;
import com.diac.awesomehardwaresupply.domain.dto.ProductPriceResponseDto;
import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.model.*;
import com.diac.awesomehardwaresupply.priceschedule.repository.*;
import com.diac.awesomehardwaresupply.priceschedule.utility.PricingAdjustments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        ProductPriceServiceImpl.class
})
public class ProductPriceServiceImplTest {

    private static final String PRICE_LEVEL = "A";

    private static final String PRICE_CODE = "C";

    private static final String CUSTOMER_NUMBER = "U";

    private static final String SKU = "SKU001";

    private static final int QUANTITY = 1;

    private static final int LIST_PRICE = 1000;

    private static final int COST = 500;

    private static final int CUSTOMER_PRICE_ADJUSTMENT = 30;

    private static final int PRODUCT_PRICE_ADJUSTMENT = 20;

    private static final int PRICE_CODE_PRICE_ADJUSTMENT = 10;

    @Autowired
    private ProductPriceService productPriceService;

    @MockBean
    private CustomerPricingRepository customerPricingRepository;

    @MockBean
    private PriceCodePricingRepository priceCodePricingRepository;

    @MockBean
    private ProductPricingRepository productPricingRepository;

    @MockBean
    private ProductDetailRepository productDetailRepository;

    @MockBean
    private PriceLevelRepository priceLevelRepository;

    @MockBean
    private PriceCodeRepository priceCodeRepository;

    @BeforeEach
    public void init() {
        Mockito.when(productDetailRepository.findAll()).thenReturn(
                List.of(
                        ProductDetail.builder()
                                .listPrice(LIST_PRICE)
                                .productSku(SKU)
                                .priceCode(PRICE_CODE)
                                .cost(COST)
                                .build()
                )
        );
        Mockito.when(productDetailRepository.findByProductSku(SKU)).thenReturn(
                Optional.of(
                        ProductDetail.builder()
                                .listPrice(LIST_PRICE)
                                .productSku(SKU)
                                .priceCode(PRICE_CODE)
                                .cost(COST)
                                .build()
                )
        );
    }

    @Test
    public void whenPriceOverrideCustomerPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_OVERRIDE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.of(
                        CustomerPricing.builder()
                                .customerNumber(CUSTOMER_NUMBER)
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(CUSTOMER_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = CUSTOMER_PRICE_ADJUSTMENT;
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenPriceOverrideProductPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_OVERRIDE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PRODUCT_PRICE_ADJUSTMENT;
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenPriceOverridePriceCodePricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_OVERRIDE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.empty()
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PRICE_CODE_PRICE_ADJUSTMENT;
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCostMarkupAmountCustomerPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.COST_MARKUP_AMOUNT;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.of(
                        CustomerPricing.builder()
                                .customerNumber(CUSTOMER_NUMBER)
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(CUSTOMER_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.costMarkupAmount(COST, CUSTOMER_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCostMarkupAmountProductPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.COST_MARKUP_AMOUNT;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.costMarkupAmount(COST, PRODUCT_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCostMarkupAmountPriceCodePricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.COST_MARKUP_AMOUNT;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.empty()
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.costMarkupAmount(COST, PRICE_CODE_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCostMarkupPercentageCustomerPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.COST_MARKUP_PERCENTAGE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.of(
                        CustomerPricing.builder()
                                .customerNumber(CUSTOMER_NUMBER)
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(CUSTOMER_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.costMarkupPercentage(COST, CUSTOMER_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCostMarkupPercentageProductPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.COST_MARKUP_PERCENTAGE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.costMarkupPercentage(COST, PRODUCT_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCostMarkupPercentagePriceCodePricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.COST_MARKUP_PERCENTAGE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.empty()
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.costMarkupPercentage(COST, PRICE_CODE_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenPriceDiscountAmountCustomerPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_DISCOUNT_AMOUNT;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.of(
                        CustomerPricing.builder()
                                .customerNumber(CUSTOMER_NUMBER)
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(CUSTOMER_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.priceDiscountAmount(LIST_PRICE, CUSTOMER_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenPriceDiscountAmountProductPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_DISCOUNT_AMOUNT;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.priceDiscountAmount(LIST_PRICE, PRODUCT_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenPriceDiscountAmountPriceCodePricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_DISCOUNT_AMOUNT;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.empty()
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.priceDiscountAmount(LIST_PRICE, PRICE_CODE_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenPriceDiscountPercentageCustomerPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_DISCOUNT_PERCENTAGE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.of(
                        CustomerPricing.builder()
                                .customerNumber(CUSTOMER_NUMBER)
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(CUSTOMER_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.priceDiscountPercentage(LIST_PRICE, CUSTOMER_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenPriceDiscountPercentageProductPricing() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_DISCOUNT_PERCENTAGE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.of(
                        ProductPricing.builder()
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRODUCT_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.priceDiscountPercentage(LIST_PRICE, PRODUCT_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenPriceDiscountPercentagePriceCodePrice() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        PricingMethod pricingMethod = PricingMethod.PRICE_DISCOUNT_PERCENTAGE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.empty()
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.of(
                        PriceCodePricing.builder()
                                .priceCode(
                                        PriceCode.builder().name(PRICE_CODE).build()
                                )
                                .priceLevel(
                                        PriceLevel.builder().name(PRICE_LEVEL).build()
                                )
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(PRICE_CODE_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build()
                                        )
                                )
                                .build()

                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = PricingAdjustments.priceDiscountPercentage(LIST_PRICE, PRICE_CODE_PRICE_ADJUSTMENT);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenNoPricingExistsThenReturnListPrice() {
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(QUANTITY)
                .build();
        PriceLevel priceLevel = PriceLevel.builder().name(PRICE_LEVEL).build();
        PriceCode priceCode = PriceCode.builder().name(PRICE_CODE).build();
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.empty()
        );
        Mockito.when(productPricingRepository.findByProductSkuAndPriceLevel(SKU, priceLevel)).thenReturn(
                Optional.empty()
        );
        Mockito.when(priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)).thenReturn(
                Optional.empty()
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        int expectedPrice = LIST_PRICE;
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCalculateByQuantity() {
        int searchQuantity = QUANTITY + 1;
        int expectedPrice = 777;
        ProductPriceRequestDto productPriceRequestDto = ProductPriceRequestDto.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .productSku(SKU)
                .priceCode(PRICE_CODE)
                .priceLevel(PRICE_LEVEL)
                .quantity(searchQuantity)
                .build();
        PricingMethod pricingMethod = PricingMethod.PRICE_OVERRIDE;
        Mockito.when(customerPricingRepository.findByCustomerNumberAndProductSku(CUSTOMER_NUMBER, SKU)).thenReturn(
                Optional.of(
                        CustomerPricing.builder()
                                .customerNumber(CUSTOMER_NUMBER)
                                .productSku(SKU)
                                .pricingSteps(
                                        List.of(
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(CUSTOMER_PRICE_ADJUSTMENT)
                                                        .minQuantity(QUANTITY)
                                                        .maxQuantity(QUANTITY)
                                                        .build(),
                                                PricingStep.builder()
                                                        .pricingMethod(pricingMethod)
                                                        .priceAdjustment(expectedPrice)
                                                        .minQuantity(searchQuantity)
                                                        .maxQuantity(searchQuantity)
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        ProductPriceResponseDto productPriceResponseDto = productPriceService.calculate(productPriceRequestDto);
        assertThat(productPriceResponseDto.calculatedPrice()).isEqualTo(expectedPrice);
    }
}