package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.Product;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {
        ProductJpaService.class
})
public class ProductJpaServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void whenFindAll() {
        List<Product> products = List.of(
                Product.builder()
                        .id(1)
                        .build(),
                Product.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(productRepository.findAll()).thenReturn(products);
        assertThat(productService.findAll()).isEqualTo(products);
        Mockito.verify(productRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<Product> expectedProducts = List.of(
                Product.builder()
                        .id(1)
                        .build(),
                Product.builder()
                        .id(2)
                        .build()
        );
        Page<Product> expectedProductPage = Mockito.mock(Page.class);
        Mockito.when(expectedProductPage.getContent()).thenReturn(expectedProducts);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(productRepository.findAll(pageRequest)).thenReturn(expectedProductPage);
        assertThat(productService.getPage(pageRequest).getContent()).isEqualTo(expectedProducts);
        Mockito.verify(productRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        Product product = Product.builder()
                .id(id)
                .build();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        assertThat(productService.findById(id)).isEqualTo(product);
        Mockito.verify(productRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenFindBySkuFound() {
        int id = 1;
        String sku = "test";
        Product product = Product.builder()
                .id(id)
                .sku(sku)
                .build();
        Mockito.when(productRepository.findBySku(sku)).thenReturn(Optional.of(product));
        assertThat(productService.findBySku(sku)).isEqualTo(product);
        Mockito.verify(productRepository).findBySku(sku);
    }

    @Test
    public void whenFindBySkuNotFoundThenThrowException() {
        String sku = "test";
        Mockito.when(productRepository.findBySku(sku)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productService.findBySku(sku)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        Product product = Product.builder()
                .name(value)
                .description(value)
                .sku(value)
                .build();
        Product savedProduct = Product.builder()
                .id(1)
                .name(value)
                .description(value)
                .sku(value)
                .build();
        Mockito.when(productRepository.save(product)).thenReturn(savedProduct);
        assertThat(productService.add(product)).isEqualTo(savedProduct);
        Mockito.verify(productRepository).save(product);
    }

    @Test
    public void whenAddViolatesDataIntegrityThenThrowException() {
        String value = "test";
        Product product = Product.builder()
                .name(value)
                .description(value)
                .sku(value)
                .build();
        Mockito.when(productRepository.save(product))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> productService.add(product)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenAddViolatesConstraintsThenThrowException() {
        String value = "test";
        Product product = Product.builder()
                .name(value)
                .description(value)
                .sku(value)
                .build();
        Mockito.when(productRepository.save(product))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> productService.add(product)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        Product product = Product.builder()
                .id(id)
                .name(value)
                .description(value)
                .sku(value)
                .build();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        assertThat(productService.update(id, product)).isEqualTo(product);
        Mockito.verify(productRepository).save(product);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        Product product = Product.builder()
                .id(id)
                .build();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productService.update(id, product)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenUpdateViolatesDataIntegrityThenThrowException() {
        int id = 1;
        String value = "test";
        Product product = Product.builder()
                .id(id)
                .name(value)
                .description(value)
                .sku(value)
                .build();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> productService.update(id, product)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdateViolatesConstraintsThenThrowException() {
        int id = 1;
        String value = "test";
        Product product = Product.builder()
                .id(id)
                .name(value)
                .description(value)
                .sku(value)
                .build();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> productService.update(id, product)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        Product product = Product.builder()
                .id(id)
                .build();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Assertions.assertAll(
                () -> productService.delete(id)
        );
        Mockito.verify(productRepository).delete(product);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}