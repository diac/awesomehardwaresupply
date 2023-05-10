package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
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
                        .build()
        );
        ProductPricing productPricingInDb = productPricingRepository.findById(productPricing.getId())
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
                        .build()
        );
        PriceLevel anotherPriceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(anotherValue)
                        .build()
        );
        productPricing.setProductSku(anotherValue);
        productPricing.setPriceLevel(anotherPriceLevel);
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