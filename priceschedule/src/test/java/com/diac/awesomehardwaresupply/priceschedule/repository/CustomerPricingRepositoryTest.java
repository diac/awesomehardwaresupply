package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
import com.diac.awesomehardwaresupply.priceschedule.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

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
                        .build()
        );
        CustomerPricing customerPricingInDb = customerPricingRepository.findById(customerPricing.getId())
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
                        .build()
        );
        customerPricing.setCustomerNumber(updatedValue);
        customerPricing.setProductSku(updatedValue);
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
                        .build()
        );
        customerPricingRepository.delete(customerPricing);
        assertThat(customerPricingRepository.findAll()).doesNotContain(customerPricing);
    }
}