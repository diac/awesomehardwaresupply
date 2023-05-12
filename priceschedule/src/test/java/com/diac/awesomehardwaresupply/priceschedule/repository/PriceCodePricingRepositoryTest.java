package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.domain.model.PriceCodePricing;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.domain.model.PricingStep;
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
public class PriceCodePricingRepositoryTest {

    @Autowired
    private PriceCodePricingRepository priceCodePricingRepository;

    @Autowired
    private PriceCodeRepository priceCodeRepository;

    @Autowired
    private PriceLevelRepository priceLevelRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        PriceCodePricing priceCodePricing = priceCodePricingRepository.save(
                PriceCodePricing.builder()
                        .priceCode(
                                priceCodeRepository.save(
                                        PriceCode.builder()
                                                .name(value)
                                                .build()
                                )
                        )
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
    }

    @Test
    public void whenFindById() {
        String value = "test";
        PriceCodePricing priceCodePricing = priceCodePricingRepository.save(
                PriceCodePricing.builder()
                        .priceCode(
                                priceCodeRepository.save(
                                        PriceCode.builder()
                                                .name(value)
                                                .build()
                                )
                        )
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
        PriceCodePricing priceCodePricingInDb = priceCodePricingRepository.findById(priceCodePricing.getId())
                .orElse(new PriceCodePricing());
        assertThat(priceCodePricingInDb).isEqualTo(priceCodePricing);
    }

    @Test
    public void whenFindByPriceLevelAndPriceCode() {
        String value = "test";
        PriceCode priceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(value)
                        .build()
        );
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        PriceCodePricing priceCodePricing = priceCodePricingRepository.save(
                PriceCodePricing.builder()
                        .priceCode(priceCode)
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
        PriceCodePricing priceCodePricingInDb
                = priceCodePricingRepository.findByPriceLevelAndPriceCode(priceLevel, priceCode)
                .orElse(new PriceCodePricing());
        assertThat(priceCodePricingInDb).isEqualTo(priceCodePricing);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        PriceCode priceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(value)
                        .build()
        );
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        PriceCodePricing priceCodePricing = priceCodePricingRepository.save(
                PriceCodePricing.builder()
                        .priceCode(priceCode)
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
        assertThat(priceCodePricing.getPriceCode()).isEqualTo(priceCode);
        assertThat(priceCodePricing.getPriceLevel()).isEqualTo(priceLevel);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        String anotherValue = value + "_another";
        PriceCode priceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(value)
                        .build()
        );
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        PriceCodePricing priceCodePricing = priceCodePricingRepository.save(
                PriceCodePricing.builder()
                        .priceCode(priceCode)
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
        PriceCode anotherPriceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(anotherValue)
                        .build()
        );
        PriceLevel anotherPriceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(anotherValue)
                        .build()
        );
        priceCodePricing.setPriceCode(anotherPriceCode);
        priceCodePricing.setPriceLevel(anotherPriceLevel);
        priceCodePricing.setPricingSteps(Collections.emptyList());
        PriceCodePricing updatedPriceCodePricing = priceCodePricingRepository.save(priceCodePricing);
        assertThat(priceCodePricing).isEqualTo(updatedPriceCodePricing);
        assertThat(priceCodePricing.getPriceLevel()).isEqualTo(updatedPriceCodePricing.getPriceLevel());
        assertThat(priceCodePricing.getPriceCode()).isEqualTo(updatedPriceCodePricing.getPriceCode());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        PriceCodePricing priceCodePricing = priceCodePricingRepository.save(
                PriceCodePricing.builder()
                        .priceCode(
                                priceCodeRepository.save(
                                        PriceCode.builder()
                                                .name(value)
                                                .build()
                                )
                        )
                        .priceLevel(
                                priceLevelRepository.save(
                                        PriceLevel.builder()
                                                .name(value)
                                                .build()
                                )
                        )
                        .build()
        );
        priceCodePricingRepository.delete(priceCodePricing);
        assertThat(priceCodePricingRepository.findAll()).doesNotContain(priceCodePricing);
    }
}