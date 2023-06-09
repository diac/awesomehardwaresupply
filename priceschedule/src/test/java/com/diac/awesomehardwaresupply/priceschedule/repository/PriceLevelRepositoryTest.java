package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceLevelRepositoryTest extends AbstractRepositoryIntegrationTest {

    @Autowired
    private PriceLevelRepository priceLevelRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        assertThat(priceLevelRepository.findAll()).contains(priceLevel);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        PriceLevel priceLevelInDb = priceLevelRepository.findById(priceLevel.getId())
                .orElse(new PriceLevel());
        assertThat(priceLevelInDb).isEqualTo(priceLevel);
    }

    @Test
    public void whenFindByName() {
        String value = "test";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        PriceLevel priceLevelInDb = priceLevelRepository.findByName(value)
                .orElse(new PriceLevel());
        assertThat(priceLevelInDb).isEqualTo(priceLevel);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        assertThat(priceLevel.getName()).isEqualTo(value);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        String updatedValue = value + "_updated";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        priceLevel.setName(updatedValue);
        PriceLevel updatedPriceLevel = priceLevelRepository.save(priceLevel);
        assertThat(priceLevel).isEqualTo(updatedPriceLevel);
        assertThat(updatedPriceLevel.getName()).isEqualTo(priceLevel.getName());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        PriceLevel priceLevel = priceLevelRepository.save(
                PriceLevel.builder()
                        .name(value)
                        .build()
        );
        priceLevelRepository.delete(priceLevel);
        assertThat(priceLevelRepository.findAll()).doesNotContain(priceLevel);
    }
}