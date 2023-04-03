package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.Product;
import com.diac.awesomehardwaresupply.knowledgebase.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        ProductRepository.class
})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .build()
        );
        assertThat(productRepository.findAll()).contains(product);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .build()
        );
        Product productInDb = productRepository.findById(product.getId()).orElse(new Product());
        assertThat(productInDb).isEqualTo(product);
    }

    @Test
    public void whenFindBySku() {
        String value = "test";
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .build()
        );
        Product productInDb = productRepository.findBySku(value).orElse(new Product());
        assertThat(productInDb).isEqualTo(product);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
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
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
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
        Product product = productRepository.save(
                Product.builder()
                        .name(value)
                        .description(value)
                        .sku(value)
                        .build()
        );
        productRepository.delete(product);
        assertThat(productRepository.findAll()).doesNotContain(product);
    }
}