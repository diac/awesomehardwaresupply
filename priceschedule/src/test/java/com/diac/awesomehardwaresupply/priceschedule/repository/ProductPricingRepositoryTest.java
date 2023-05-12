package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.domain.model.PricingStep;
import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
import com.diac.awesomehardwaresupply.priceschedule.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class
})
public class ProductPricingRepositoryTest {

    @Autowired
    private ProductPricingRepository productPricingRepository;

    @Autowired
    private PriceLevelRepository priceLevelRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        ProductPricing productPricing = productPricingRepository.save(
                ProductPricing.builder()
                        .productSku(value)
                        .priceLevel(
                                priceLevelRepository.save(
                                        PriceLevel.builder()
                                                .name(value)
                                                .build()
                                )
                        )
                        .pricingSteps(
                                List.of(
                                        PricingStep.builder()
                                                .priceAdjustment(1000)
                                                .maxQuantity(1000)
                                                .minQuantity(1000)
                                                .pricingMethod(PricingMethod.PRICE_OVERRIDE)
                                                .build()
                                )
                        )
                        .build()
        );
        assertThat(productPricingRepository.findAll()).contains(productPricing);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        ProductPricing productPricing = productPricingRepository.save(
                ProductPricing.builder()
                        .productSku(value)
                        .priceLevel(
                                priceLevelRepository.save(
                                        PriceLevel.builder()
                                                .name(value)
                                                .build()
                                )
                        )
                        .pricingSteps(
                                List.of(
                                        PricingStep.builder()
                                                .priceAdjustment(1000)
                                                .maxQuantity(1000)
                                                .minQuantity(1000)
                                                .pricingMethod(PricingMethod.PRICE_OVERRIDE)
                                                .build()
                                )
                        )
                        .build()
        );
        ProductPricing productPricingInDb = productPricingRepository.findById(productPricing.getId())
                .orElse(new ProductPricing());
        assertThat(productPricingInDb).isEqualTo(productPricing);
    }

    @Test
    public void whenFindByProductSkuAndPriceLevel() {
        String value = "test";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        ProductPricing productPricing = productPricingRepository.save(
                ProductPricing.builder()
                        .productSku(value)
                        .priceLevel(priceLevel)
                        .pricingSteps(
                                List.of(
                                        PricingStep.builder()
                                                .priceAdjustment(1000)
                                                .maxQuantity(1000)
                                                .minQuantity(1000)
                                                .pricingMethod(PricingMethod.PRICE_OVERRIDE)
                                                .build()
                                )
                        )
                        .build()
        );
        ProductPricing productPricingInDb = productPricingRepository.findByProductSkuAndPriceLevel(value, priceLevel)
                .orElse(new ProductPricing());
        assertThat(productPricingInDb).isEqualTo(productPricing);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        ProductPricing productPricing = productPricingRepository.save(
                ProductPricing.builder()
                        .productSku(value)
                        .priceLevel(priceLevel)
                        .pricingSteps(
                                List.of(
                                        PricingStep.builder()
                                                .priceAdjustment(1000)
                                                .maxQuantity(1000)
                                                .minQuantity(1000)
                                                .pricingMethod(PricingMethod.PRICE_OVERRIDE)
                                                .build()
                                )
                        )
                        .build()
        );
        assertThat(productPricing.getProductSku()).isEqualTo(value);
        assertThat(productPricing.getPriceLevel()).isEqualTo(priceLevel);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        String anotherValue = value + "_another";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        ProductPricing productPricing = productPricingRepository.save(
                ProductPricing.builder()
                        .productSku(value)
                        .priceLevel(priceLevel)
                        .pricingSteps(
                                List.of(
                                        PricingStep.builder()
                                                .priceAdjustment(1000)
                                                .maxQuantity(1000)
                                                .minQuantity(1000)
                                                .pricingMethod(PricingMethod.PRICE_OVERRIDE)
                                                .build()
                                )
                        )
                        .build()
        );
        PriceLevel anotherPriceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(anotherValue)
                        .build()
        );
        productPricing.setProductSku(anotherValue);
        productPricing.setPriceLevel(anotherPriceLevel);
        productPricing.setPricingSteps(Collections.emptyList());
        ProductPricing updatedProductPricing = productPricingRepository.save(productPricing);
        assertThat(productPricing).isEqualTo(updatedProductPricing);
        assertThat(productPricing.getProductSku()).isEqualTo(updatedProductPricing.getProductSku());
        assertThat(productPricing.getPriceLevel()).isEqualTo(updatedProductPricing.getPriceLevel());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        ProductPricing productPricing = productPricingRepository.save(
                ProductPricing.builder()
                        .productSku(value)
                        .priceLevel(
                                priceLevelRepository.save(
                                        PriceLevel.builder()
                                                .name(value)
                                                .build()
                                )
                        )
                        .build()
        );
        productPricingRepository.delete(productPricing);
        assertThat(productPricingRepository.findAll()).doesNotContain(productPricing);
    }
}