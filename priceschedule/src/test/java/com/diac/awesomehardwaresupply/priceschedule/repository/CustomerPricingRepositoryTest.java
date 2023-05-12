package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
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
public class CustomerPricingRepositoryTest {

    @Autowired
    private CustomerPricingRepository customerPricingRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        CustomerPricing customerPricing = customerPricingRepository.save(
                CustomerPricing.builder()
                        .customerNumber(value)
                        .productSku(value)
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
        assertThat(customerPricingRepository.findAll()).contains(customerPricing);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        CustomerPricing customerPricing = customerPricingRepository.save(
                CustomerPricing.builder()
                        .customerNumber(value)
                        .productSku(value)
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
        CustomerPricing customerPricingInDb = customerPricingRepository.findById(customerPricing.getId())
                .orElse(new CustomerPricing());
        assertThat(customerPricingInDb).isEqualTo(customerPricing);
    }

    @Test
    public void whenFindByCustomerNumberAndProductSku() {
        String value = "test";
        CustomerPricing customerPricing = customerPricingRepository.save(
                CustomerPricing.builder()
                        .customerNumber(value)
                        .productSku(value)
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
        CustomerPricing customerPricingInDb = customerPricingRepository.findByCustomerNumberAndProductSku(value, value)
                .orElse(new CustomerPricing());
        assertThat(customerPricingInDb).isEqualTo(customerPricing);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        CustomerPricing customerPricing = customerPricingRepository.save(
                CustomerPricing.builder()
                        .customerNumber(value)
                        .productSku(value)
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
        assertThat(customerPricing.getCustomerNumber()).isEqualTo(value);
        assertThat(customerPricing.getProductSku()).isEqualTo(value);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        String updatedValue = value + "_updated";
        CustomerPricing customerPricing = customerPricingRepository.save(
                CustomerPricing.builder()
                        .customerNumber(value)
                        .productSku(value)
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
        customerPricing.setCustomerNumber(updatedValue);
        customerPricing.setProductSku(updatedValue);
        customerPricing.setPricingSteps(Collections.emptyList());
        CustomerPricing updatedCustomerPricing = customerPricingRepository.save(customerPricing);
        assertThat(customerPricing).isEqualTo(updatedCustomerPricing);
        assertThat(customerPricing.getProductSku()).isEqualTo(updatedCustomerPricing.getProductSku());
        assertThat(customerPricing.getCustomerNumber()).isEqualTo(updatedCustomerPricing.getCustomerNumber());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        CustomerPricing customerPricing = customerPricingRepository.save(
                CustomerPricing.builder()
                        .customerNumber(value)
                        .productSku(value)
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
        customerPricingRepository.delete(customerPricing);
        assertThat(customerPricingRepository.findAll()).doesNotContain(customerPricing);
    }
}