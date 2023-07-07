package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
import com.diac.awesomehardwaresupply.priceschedule.repository.CustomerPricingRepository;
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
        CustomerPricingJpaService.class
})
public class CustomerPricingJpaServiceTest {

    @Autowired
    private CustomerPricingService customerPricingService;

    @MockBean
    private CustomerPricingRepository customerPricingRepository;

    @Test
    public void whenFindAll() {
        List<CustomerPricing> customerPricings = List.of(
                CustomerPricing.builder()
                        .id(1)
                        .build(),
                CustomerPricing.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(customerPricingRepository.findAll()).thenReturn(customerPricings);
        assertThat(customerPricingService.findAll()).isEqualTo(customerPricings);
        Mockito.verify(customerPricingRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<CustomerPricing> expectedCustomerPricings = List.of(
                CustomerPricing.builder()
                        .id(1)
                        .build(),
                CustomerPricing.builder()
                        .id(2)
                        .build()
        );
        Page<CustomerPricing> expectedCustomerPricingPage = (Page<CustomerPricing>) Mockito.mock(Page.class);
        Mockito.when(expectedCustomerPricingPage.getContent()).thenReturn(expectedCustomerPricings);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(customerPricingRepository.findAll(pageRequest)).thenReturn(expectedCustomerPricingPage);
        assertThat(customerPricingService.getPage(pageRequest).getContent()).isEqualTo(expectedCustomerPricings);
        Mockito.verify(customerPricingRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .build();
        Mockito.when(customerPricingRepository.findById(id)).thenReturn(Optional.of(customerPricing));
        assertThat(customerPricingService.findById(id)).isEqualTo(customerPricing);
        Mockito.verify(customerPricingRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(customerPricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> customerPricingService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        CustomerPricing customerPricing = CustomerPricing.builder()
                .customerNumber(value)
                .productSku(value)
                .build();
        CustomerPricing savedCustomerPricing = CustomerPricing.builder()
                .id(1)
                .customerNumber(value)
                .productSku(value)
                .build();
        Mockito.when(customerPricingRepository.save(customerPricing)).thenReturn(savedCustomerPricing);
        assertThat(customerPricingService.add(customerPricing)).isEqualTo(savedCustomerPricing);
        Mockito.verify(customerPricingRepository).save(customerPricing);
    }

    @Test
    public void whenAddViolatesDataIntegrityThenThrowException() {
        String value = "test";
        CustomerPricing customerPricing = CustomerPricing.builder()
                .customerNumber(value)
                .productSku(value)
                .build();
        Mockito.when(customerPricingRepository.save(customerPricing))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> customerPricingService.add(customerPricing)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenAddViolatesConstraintsThenThrowException() {
        String value = "test";
        CustomerPricing customerPricing = CustomerPricing.builder()
                .customerNumber(value)
                .productSku(value)
                .build();
        Mockito.when(customerPricingRepository.save(customerPricing))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> customerPricingService.add(customerPricing)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .customerNumber(value)
                .productSku(value)
                .build();
        Mockito.when(customerPricingRepository.findById(id)).thenReturn(Optional.of(customerPricing));
        Mockito.when(customerPricingRepository.save(customerPricing)).thenReturn(customerPricing);
        assertThat(customerPricingService.update(id, customerPricing)).isEqualTo(customerPricing);
        Mockito.verify(customerPricingRepository).save(customerPricing);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .build();
        Mockito.when(customerPricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> customerPricingService.update(id, customerPricing)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenUpdateViolatesDataIntegrityThenThrowException() {
        int id = 1;
        String value = "test";
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .customerNumber(value)
                .productSku(value)
                .build();
        Mockito.when(customerPricingRepository.findById(id)).thenReturn(Optional.of(customerPricing));
        Mockito.when(customerPricingRepository.save(customerPricing))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> customerPricingService.update(id, customerPricing)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdateViolatesConstraintsThenThrowException() {
        int id = 1;
        String value = "test";
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .customerNumber(value)
                .productSku(value)
                .build();
        Mockito.when(customerPricingRepository.findById(id)).thenReturn(Optional.of(customerPricing));
        Mockito.when(customerPricingRepository.save(customerPricing))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> customerPricingService.update(id, customerPricing)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .build();
        Mockito.when(customerPricingRepository.findById(id)).thenReturn(Optional.of(customerPricing));
        Assertions.assertAll(
                () -> customerPricingService.delete(id)
        );
        Mockito.verify(customerPricingRepository).delete(customerPricing);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(customerPricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> customerPricingService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}