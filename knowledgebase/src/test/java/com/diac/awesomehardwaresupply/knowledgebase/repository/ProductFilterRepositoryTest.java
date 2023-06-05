package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductFilterRepositoryTest extends AbstractPostgreSQLContainerInitializer {

    @Autowired
    private ProductFilterRepository productFilterRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        ProductFilter productFilter = productFilterRepository.save(
                ProductFilter.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        assertThat(productFilterRepository.findAll()).contains(productFilter);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        ProductFilter productFilter = productFilterRepository.save(
                ProductFilter.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        ProductFilter productFilterInDb = productFilterRepository.findById(productFilter.getId())
                .orElse(new ProductFilter());
        assertThat(productFilterInDb).isEqualTo(productFilter);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        ProductFilter productFilter = productFilterRepository.save(
                ProductFilter.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        assertThat(productFilter.getName()).isEqualTo(value);
        assertThat(productFilter.getDescription()).isEqualTo(value);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        String updatedValue = value + "_updated";
        ProductFilter productFilter = productFilterRepository.save(
                ProductFilter.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        productFilter.setName(updatedValue);
        productFilter.setDescription(updatedValue);
        ProductFilter updatedProductFilter = productFilterRepository.save(productFilter);
        assertThat(productFilter).isEqualTo(updatedProductFilter);
        assertThat(productFilter.getName()).isEqualTo(updatedProductFilter.getName());
        assertThat(productFilter.getDescription()).isEqualTo(updatedProductFilter.getDescription());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        ProductFilter productFilter = productFilterRepository.save(
                ProductFilter.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        productFilterRepository.delete(productFilter);
        assertThat(productFilterRepository.findAll()).doesNotContain(productFilter);
    }
}