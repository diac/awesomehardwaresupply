package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
import com.diac.awesomehardwaresupply.priceschedule.repository.ProductPricingRepository;
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
        ProductPricingJpaService.class
})
public class ProductPricingJpaServiceTest {

    @Autowired
    private ProductPricingService productPricingService;

    @MockBean
    private ProductPricingRepository productPricingRepository;

    @Test
    public void whenFindAll() {
        List<ProductPricing> productPricings = List.of(
                ProductPricing.builder()
                        .id(1)
                        .build(),
                ProductPricing.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(productPricingRepository.findAll()).thenReturn(productPricings);
        assertThat(productPricingService.findAll()).isEqualTo(productPricings);
        Mockito.verify(productPricingRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<ProductPricing> expectedProductPricings = List.of(
                ProductPricing.builder()
                        .id(1)
                        .build(),
                ProductPricing.builder()
                        .id(2)
                        .build()
        );
        Page<ProductPricing> expectedProductPricingPage = (Page<ProductPricing>) Mockito.mock(Page.class);
        Mockito.when(expectedProductPricingPage.getContent()).thenReturn(expectedProductPricings);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(productPricingRepository.findAll(pageRequest)).thenReturn(expectedProductPricingPage);
        assertThat(productPricingService.getPage(pageRequest).getContent()).isEqualTo(expectedProductPricings);
        Mockito.verify(productPricingRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .build();
        Mockito.when(productPricingRepository.findById(id)).thenReturn(Optional.of(productPricing));
        assertThat(productPricingService.findById(id)).isEqualTo(productPricing);
        Mockito.verify(productPricingRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(productPricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productPricingService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        ProductPricing productPricing = ProductPricing.builder()
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        ProductPricing savedProductPricing = ProductPricing.builder()
                .id(1)
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        Mockito.when(productPricingRepository.save(productPricing)).thenReturn(savedProductPricing);
        assertThat(productPricingService.add(productPricing)).isEqualTo(savedProductPricing);
        Mockito.verify(productPricingRepository).save(productPricing);
    }

    @Test
    public void whenAddViolatesDataIntegrityThenThrowException() {
        String value = "test";
        ProductPricing productPricing = ProductPricing.builder()
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        Mockito.when(productPricingRepository.save(productPricing))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> productPricingService.add(productPricing)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenAddViolatesConstraintsThenThrowException() {
        String value = "test";
        ProductPricing productPricing = ProductPricing.builder()
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        Mockito.when(productPricingRepository.save(productPricing))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> productPricingService.add(productPricing)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        Mockito.when(productPricingRepository.findById(id)).thenReturn(Optional.of(productPricing));
        Mockito.when(productPricingRepository.save(productPricing)).thenReturn(productPricing);
        assertThat(productPricingService.update(id, productPricing)).isEqualTo(productPricing);
        Mockito.verify(productPricingRepository).save(productPricing);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .build();
        Mockito.when(productPricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productPricingService.update(id, productPricing)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenUpdateViolatesDataIntegrityThenThrowException() {
        int id = 1;
        String value = "test";
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        Mockito.when(productPricingRepository.findById(id)).thenReturn(Optional.of(productPricing));
        Mockito.when(productPricingRepository.save(productPricing))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> productPricingService.update(id, productPricing)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdateViolatesConstraintsThenThrowException() {
        int id = 1;
        String value = "test";
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        Mockito.when(productPricingRepository.findById(id)).thenReturn(Optional.of(productPricing));
        Mockito.when(productPricingRepository.save(productPricing))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> productPricingService.update(id, productPricing)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .build();
        Mockito.when(productPricingRepository.findById(id)).thenReturn(Optional.of(productPricing));
        Assertions.assertAll(
                () -> productPricingService.delete(id)
        );
        Mockito.verify(productPricingRepository).delete(productPricing);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(productPricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productPricingService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}