package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductFilter;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductFilterRepository;
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
        ProductFilterJpaService.class
})
public class ProductFilterJpaServiceTest {

    @Autowired
    private ProductFilterService productFilterService;

    @MockBean
    private ProductFilterRepository productFilterRepository;

    @Test
    public void whenFindAll() {
        List<ProductFilter> productFilters = List.of(
                ProductFilter.builder()
                        .id(1)
                        .build(),
                ProductFilter.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(productFilterRepository.findAll()).thenReturn(productFilters);
        assertThat(productFilterService.findAll()).isEqualTo(productFilters);
        Mockito.verify(productFilterRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<ProductFilter> expectedProductFilters = List.of(
                ProductFilter.builder()
                        .id(1)
                        .build(),
                ProductFilter.builder()
                        .id(2)
                        .build()
        );
        Page<ProductFilter> expectedProductFilterPage = Mockito.mock(Page.class);
        Mockito.when(expectedProductFilterPage.getContent()).thenReturn(expectedProductFilters);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(productFilterRepository.findAll(pageRequest)).thenReturn(expectedProductFilterPage);
        assertThat(productFilterService.getPage(pageRequest).getContent()).isEqualTo(expectedProductFilters);
        Mockito.verify(productFilterRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .build();
        Mockito.when(productFilterRepository.findById(id)).thenReturn(Optional.of(productFilter));
        assertThat(productFilterService.findById(id)).isEqualTo(productFilter);
        Mockito.verify(productFilterRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(productFilterRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productFilterService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        ProductFilter productFilter = ProductFilter.builder()
                .name(value)
                .description(value)
                .build();
        ProductFilter savedProductFilter = ProductFilter.builder()
                .id(1)
                .name(value)
                .description(value)
                .build();
        Mockito.when(productFilterRepository.save(productFilter)).thenReturn(savedProductFilter);
        assertThat(productFilterService.add(productFilter)).isEqualTo(savedProductFilter);
        Mockito.verify(productFilterRepository).save(productFilter);
    }

    @Test
    public void whenAddViolatesDataIntegrityThenThrowException() {
        String value = "test";
        ProductFilter productFilter = ProductFilter.builder()
                .name(value)
                .description(value)
                .build();
        Mockito.when(productFilterRepository.save(productFilter))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> productFilterService.add(productFilter)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenAddViolatesConstraintsThenThrowException() {
        String value = "test";
        ProductFilter productFilter = ProductFilter.builder()
                .name(value)
                .description(value)
                .build();
        Mockito.when(productFilterRepository.save(productFilter))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> productFilterService.add(productFilter)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        Mockito.when(productFilterRepository.findById(id)).thenReturn(Optional.of(productFilter));
        Mockito.when(productFilterRepository.save(productFilter)).thenReturn(productFilter);
        assertThat(productFilterService.update(id, productFilter)).isEqualTo(productFilter);
        Mockito.verify(productFilterRepository).save(productFilter);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .build();
        Mockito.when(productFilterRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productFilterService.update(id, productFilter)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenUpdateViolatesDataIntegrityThenThrowException() {
        int id = 1;
        String value = "test";
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        Mockito.when(productFilterRepository.findById(id)).thenReturn(Optional.of(productFilter));
        Mockito.when(productFilterRepository.save(productFilter))
                .thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(
                () -> productFilterService.update(id, productFilter)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenUpdateViolatesConstraintsThenThrowException() {
        int id = 1;
        String value = "test";
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        Mockito.when(productFilterRepository.findById(id)).thenReturn(Optional.of(productFilter));
        Mockito.when(productFilterRepository.save(productFilter))
                .thenThrow(ConstraintViolationException.class);
        assertThatThrownBy(
                () -> productFilterService.update(id, productFilter)
        ).isInstanceOf(ResourceConstraintViolationException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .build();
        Mockito.when(productFilterRepository.findById(id)).thenReturn(Optional.of(productFilter));
        Assertions.assertAll(
                () -> productFilterService.delete(id)
        );
        Mockito.verify(productFilterRepository).delete(productFilter);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(productFilterRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productFilterService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}