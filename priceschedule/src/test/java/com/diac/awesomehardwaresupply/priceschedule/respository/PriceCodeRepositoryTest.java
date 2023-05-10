package com.diac.awesomehardwaresupply.priceschedule.respository;

import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.priceschedule.config.DataConfig;
import com.diac.awesomehardwaresupply.priceschedule.repository.PriceCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class
})
public class PriceCodeRepositoryTest {

    @Autowired
    private PriceCodeRepository priceCodeRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        PriceCode priceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(value)
                        .build()
        );
        assertThat(priceCodeRepository.findAll()).contains(priceCode);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        PriceCode priceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(value)
                        .build()
        );
        PriceCode priceCodeInDb = priceCodeRepository.findById(priceCode.getId())
                .orElse(new PriceCode());
        assertThat(priceCodeInDb).isEqualTo(priceCode);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        PriceCode priceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(value)
                        .build()
        );
        assertThat(priceCode.getName()).isEqualTo(value);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        String updatedValue = value + "_updated";
        PriceCode priceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(value)
                        .build()
        );
        priceCode.setName(updatedValue);
        PriceCode updatedPriceCode = priceCodeRepository.save(priceCode);
        assertThat(priceCode).isEqualTo(updatedPriceCode);
        assertThat(priceCode.getName()).isEqualTo(updatedPriceCode.getName());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        PriceCode priceCode = priceCodeRepository.save(
                PriceCode.builder()
                        .name(value)
                        .build()
        );
        priceCodeRepository.delete(priceCode);
        assertThat(priceCodeRepository.findAll()).doesNotContain(priceCode);
    }
}