package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.Product;
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
        ProductRepository.class
})
public class ProductRepositoryTest implements PostgreSQLContainerInitializer {

    @Autowired
    private ProductRepository productRepository;

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
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .productCategory(productCategory)
                        .build()
        );
        assertThat(productRepository.findAll()).contains(product);
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
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .productCategory(productCategory)
                        .build()
        );
        Product productInDb = productRepository.findById(product.getId()).orElse(new Product());
        assertThat(productInDb).isEqualTo(product);
    }

    @Test
    public void whenFindBySku() {
        String value = "test";
        ProductCategory productCategory = productCategoryRepository.save(
                ProductCategory.builder()
                        .name(value)
                        .description(value)
                        .build()
        );
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .productCategory(productCategory)
                        .build()
        );
        Product productInDb = productRepository.findBySku(value).orElse(new Product());
        assertThat(productInDb).isEqualTo(product);
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
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .productCategory(productCategory)
                        .build()
        );
        assertThat(product.getName()).isEqualTo(value);
        assertThat(product.getDescription()).isEqualTo(value);
        assertThat(product.getSku()).isEqualTo(value);
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
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .productCategory(productCategory)
                        .build()
        );
        product.setName(updatedValue);
        product.setDescription(updatedValue);
        product.setSku(updatedValue);
        Product updatedProduct = productRepository.save(product);
        assertThat(product).isEqualTo(updatedProduct);
        assertThat(product.getName()).isEqualTo(updatedProduct.getName());
        assertThat(product.getDescription()).isEqualTo(updatedProduct.getDescription());
        assertThat(product.getSku()).isEqualTo(updatedProduct.getSku());
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
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .productCategory(productCategory)
                        .build()
        );
        productRepository.delete(product);
        assertThat(productRepository.findAll()).doesNotContain(product);
    }
}