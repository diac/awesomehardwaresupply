package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import com.diac.awesomehardwaresupply.priceschedule.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {
        DataConfig.class
})
public class ProductDetailRepositoryTest implements PostgreSQLContainerInitializer {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Test
    public void whenFindAll() {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = productDetailRepository.save(
                ProductDetail.builder()
                        .productSku(value)
                        .listPrice(number)
                        .cost(number)
                        .priceCode(value)
                        .build()
        );
        assertThat(productDetailRepository.findAll()).contains(productDetail);
    }

    @Test
    public void whenFindById() {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = productDetailRepository.save(
                ProductDetail.builder()
                        .productSku(value)
                        .listPrice(number)
                        .cost(number)
                        .priceCode(value)
                        .build()
        );
        ProductDetail productDetailInDb = productDetailRepository.findById(productDetail.getId())
                .orElse(new ProductDetail());
        assertThat(productDetailInDb).isEqualTo(productDetail);
    }

    @Test
    public void whenFindByProductSku() {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = productDetailRepository.save(
                ProductDetail.builder()
                        .productSku(value)
                        .listPrice(number)
                        .cost(number)
                        .priceCode(value)
                        .build()
        );
        ProductDetail productDetailInDb = productDetailRepository.findByProductSku(productDetail.getProductSku())
                .orElse(new ProductDetail());
        assertThat(productDetailInDb).isEqualTo(productDetail);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = productDetailRepository.save(
                ProductDetail.builder()
                        .productSku(value)
                        .listPrice(number)
                        .cost(number)
                        .priceCode(value)
                        .build()
        );
        assertThat(productDetail.getProductSku()).isEqualTo(value);
        assertThat(productDetail.getListPrice()).isEqualTo(number);
        assertThat(productDetail.getCost()).isEqualTo(number);
        assertThat(productDetail.getPriceCode()).isEqualTo(value);
    }

    @Test
    public void whenUpdate() {
        String value = "test";
        int number = 1234;
        String updatedValue = value + "_updated";
        int updatedNumber = 5678;
        ProductDetail productDetail = productDetailRepository.save(
                ProductDetail.builder()
                        .productSku(value)
                        .listPrice(number)
                        .cost(number)
                        .priceCode(value)
                        .build()
        );
        productDetail.setProductSku(updatedValue);
        productDetail.setListPrice(updatedNumber);
        productDetail.setCost(updatedNumber);
        productDetail.setPriceCode(updatedValue);
        ProductDetail updatedProductDetail = productDetailRepository.save(productDetail);
        assertThat(productDetail).isEqualTo(updatedProductDetail);
        assertThat(productDetail.getProductSku()).isEqualTo(updatedProductDetail.getProductSku());
        assertThat(productDetail.getListPrice()).isEqualTo(updatedProductDetail.getListPrice());
        assertThat(productDetail.getCost()).isEqualTo(updatedProductDetail.getCost());
        assertThat(productDetail.getPriceCode()).isEqualTo(updatedProductDetail.getPriceCode());
    }

    @Test
    public void whenDelete() {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = productDetailRepository.save(
                ProductDetail.builder()
                        .productSku(value)
                        .listPrice(number)
                        .cost(number)
                        .priceCode(value)
                        .build()
        );
        productDetailRepository.delete(productDetail);
        assertThat(productDetailRepository.findAll()).doesNotContain(productDetail);
    }
}