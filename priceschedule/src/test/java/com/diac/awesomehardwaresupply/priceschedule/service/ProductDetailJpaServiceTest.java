package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import com.diac.awesomehardwaresupply.priceschedule.repository.ProductDetailRepository;
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
        ProductDetailJpaService.class
})
public class ProductDetailJpaServiceTest {

    @Autowired
    private ProductDetailService productDetailService;

    @MockBean
    private ProductDetailRepository productDetailRepository;

    @Test
    public void whenFindAll() {
        List<ProductDetail> productDetails = List.of(
                ProductDetail.builder()
                        .id(1)
                        .build(),
                ProductDetail.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(productDetailRepository.findAll()).thenReturn(productDetails);
        assertThat(productDetailService.findAll()).isEqualTo(productDetails);
        Mockito.verify(productDetailRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<ProductDetail> expectedProductDetails = List.of(
                ProductDetail.builder()
                        .id(1)
                        .build(),
                ProductDetail.builder()
                        .id(2)
                        .build()
        );
        Page<ProductDetail> expectedProductDetailPage = (Page<ProductDetail>) Mockito.mock(Page.class);
        Mockito.when(expectedProductDetailPage.getContent()).thenReturn(expectedProductDetails);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(productDetailRepository.findAll(pageRequest)).thenReturn(expectedProductDetailPage);
        assertThat(productDetailService.getPage(pageRequest).getContent()).isEqualTo(expectedProductDetails);
        Mockito.verify(productDetailRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .build();
        Mockito.when(productDetailRepository.findById(id)).thenReturn(Optional.of(productDetail));
        assertThat(productDetailService.findById(id)).isEqualTo(productDetail);
        Mockito.verify(productDetailRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(productDetailRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productDetailService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        ProductDetail savedProductDetail = ProductDetail.builder()
                .id(1)
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        Mockito.when(productDetailRepository.save(productDetail)).thenReturn(savedProductDetail);
        assertThat(productDetailService.add(productDetail)).isEqualTo(savedProductDetail);
        Mockito.verify(productDetailRepository).save(productDetail);
    }

    @Test
    public void whenAddViolatesDataIntegrityThenThrowException() {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        Mockito.when(productDetailRepository.save(productDetail))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> productDetailService.add(productDetail)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenAddViolatesConstraintsThenThrowException() {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        Mockito.when(productDetailRepository.save(productDetail))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> productDetailService.add(productDetail)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        Mockito.when(productDetailRepository.findById(id)).thenReturn(Optional.of(productDetail));
        Mockito.when(productDetailRepository.save(productDetail)).thenReturn(productDetail);
        assertThat(productDetailService.update(id, productDetail)).isEqualTo(productDetail);
        Mockito.verify(productDetailRepository).save(productDetail);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .build();
        Mockito.when(productDetailRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productDetailService.update(id, productDetail)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenUpdateViolatesDataIntegrityThenThrowException() {
        int id = 1;
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        Mockito.when(productDetailRepository.findById(id)).thenReturn(Optional.of(productDetail));
        Mockito.when(productDetailRepository.save(productDetail))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> productDetailService.update(id, productDetail)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdateViolatesConstraintsThenThrowException() {
        int id = 1;
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        Mockito.when(productDetailRepository.findById(id)).thenReturn(Optional.of(productDetail));
        Mockito.when(productDetailRepository.save(productDetail))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> productDetailService.update(id, productDetail)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .build();
        Mockito.when(productDetailRepository.findById(id)).thenReturn(Optional.of(productDetail));
        Assertions.assertAll(
                () -> productDetailService.delete(id)
        );
        Mockito.verify(productDetailRepository).delete(productDetail);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(productDetailRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productDetailService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}