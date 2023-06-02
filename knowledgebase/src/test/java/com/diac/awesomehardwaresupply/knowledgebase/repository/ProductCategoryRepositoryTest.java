package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductCategory;
import com.diac.awesomehardwaresupply.knowledgebase.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {
        DataConfig.class,
        ProductCategory.class
})
public class ProductCategoryRepositoryTest implements PostgreSQLContainerInitializer {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        ProductCategory productCategory = productCategoryRepository.save(
                ProductCategory.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        assertThat(productCategoryRepository.findAll()).contains(productCategory);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        ProductCategory productCategory = productCategoryRepository.save(
                ProductCategory.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        ProductCategory productCategoryInDb = productCategoryRepository.findById(productCategory.getId())
                .orElse(new ProductCategory());
        assertThat(productCategoryInDb).isEqualTo(productCategory);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        ProductCategory productCategory = productCategoryRepository.save(
                ProductCategory.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        assertThat(productCategory.getName()).isEqualTo(value);
        assertThat(productCategory.getDescription()).isEqualTo(value);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        String updatedValue = value + "_updated";
        ProductCategory productCategory = productCategoryRepository.save(
                ProductCategory.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        productCategory.setName(updatedValue);
        productCategory.setDescription(updatedValue);
        ProductCategory updatedProductCategory = productCategoryRepository.save(productCategory);
        assertThat(productCategory).isEqualTo(updatedProductCategory);
        assertThat(productCategory.getName()).isEqualTo(updatedProductCategory.getName());
        assertThat(productCategory.getDescription()).isEqualTo(updatedProductCategory.getDescription());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        ProductCategory productCategory = productCategoryRepository.save(
                ProductCategory.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        productCategoryRepository.delete(productCategory);
        assertThat(productCategoryRepository.findAll()).doesNotContain(productCategory);
    }
}