package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductSpecificationRepositoryTest extends AbstractPostgreSQLContainerInitializer {

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        ProductSpecification productSpecification = productSpecificationRepository.save(
                ProductSpecification.builder()
                        .name(value)
                        .description(value)
                        .units(value)
                        .build()
        );
        assertThat(productSpecificationRepository.findAll()).contains(productSpecification);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        ProductSpecification productSpecification = productSpecificationRepository.save(
                ProductSpecification.builder()
                        .name(value)
                        .description(value)
                        .units(value)
                        .build()
        );
        ProductSpecification productSpecificationInDb
                = productSpecificationRepository.findById(productSpecification.getId())
                .orElse(new ProductSpecification());
        assertThat(productSpecificationInDb).isEqualTo(productSpecification);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        ProductSpecification productSpecification = productSpecificationRepository.save(
                ProductSpecification.builder()
                        .name(value)
                        .description(value)
                        .units(value)
                        .build()
        );
        assertThat(productSpecification.getName()).isEqualTo(value);
        assertThat(productSpecification.getDescription()).isEqualTo(value);
        assertThat(productSpecification.getUnits()).isEqualTo(value);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        String updatedValue = value + "_updated";
        ProductSpecification productSpecification = productSpecificationRepository.save(
                ProductSpecification.builder()
                        .name(value)
                        .description(value)
                        .units(value)
                        .build()
        );
        productSpecification.setName(updatedValue);
        productSpecification.setDescription(updatedValue);
        productSpecification.setUnits(updatedValue);
        ProductSpecification updatedProductSpecification = productSpecificationRepository.save(productSpecification);
        assertThat(productSpecification).isEqualTo(updatedProductSpecification);
        assertThat(productSpecification.getName()).isEqualTo(updatedProductSpecification.getName());
        assertThat(productSpecification.getDescription()).isEqualTo(updatedProductSpecification.getDescription());
        assertThat(productSpecification.getUnits()).isEqualTo(updatedProductSpecification.getDescription());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        ProductSpecification productSpecification = productSpecificationRepository.save(
                ProductSpecification.builder()
                        .name(value)
                        .description(value)
                        .units(value)
                        .build()
        );
        productSpecificationRepository.delete(productSpecification);
        assertThat(productSpecificationRepository.findAll()).doesNotContain(productSpecification);
    }
}