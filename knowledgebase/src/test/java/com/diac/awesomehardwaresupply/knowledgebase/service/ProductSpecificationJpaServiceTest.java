package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductSpecification;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductSpecificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {
        ProductSpecificationJpaService.class
})
public class ProductSpecificationJpaServiceTest {

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @MockBean
    private ProductSpecificationRepository productSpecificationRepository;

    @Test
    public void whenFindAll() {
        List<ProductSpecification> productSpecifications = List.of(
                ProductSpecification.builder()
                        .id(1)
                        .build(),
                ProductSpecification.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(productSpecificationRepository.findAll()).thenReturn(productSpecifications);
        assertThat(productSpecificationService.findAll()).isEqualTo(productSpecifications);
        Mockito.verify(productSpecificationRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<ProductSpecification> expectedProductSpecifications = List.of(
                ProductSpecification.builder()
                        .id(1)
                        .build(),
                ProductSpecification.builder()
                        .id(2)
                        .build()
        );
        Page<ProductSpecification> expectedProductSpecificationPage
                = (Page<ProductSpecification>) Mockito.mock(Page.class);
        Mockito.when(expectedProductSpecificationPage.getContent()).thenReturn(expectedProductSpecifications);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(productSpecificationRepository.findAll(pageRequest)).thenReturn(expectedProductSpecificationPage);
        assertThat(productSpecificationService.getPage(pageRequest).getContent())
                .isEqualTo(expectedProductSpecifications);
        Mockito.verify(productSpecificationRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        ProductSpecification productSpecification = ProductSpecification.builder()
                .id(id)
                .build();
        Mockito.when(productSpecificationRepository.findById(id)).thenReturn(Optional.of(productSpecification));
        assertThat(productSpecificationService.findById(id)).isEqualTo(productSpecification);
        Mockito.verify(productSpecificationRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(productSpecificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productSpecificationService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        ProductSpecification productSpecification = ProductSpecification.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        Mockito.when(productSpecificationRepository.findById(id)).thenReturn(Optional.of(productSpecification));
        Mockito.when(productSpecificationRepository.save(productSpecification)).thenReturn(productSpecification);
        assertThat(productSpecificationService.update(id, productSpecification)).isEqualTo(productSpecification);
        Mockito.verify(productSpecificationRepository).save(productSpecification);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        ProductSpecification productSpecification = ProductSpecification.builder()
                .id(id)
                .build();
        Mockito.when(productSpecificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productSpecificationService.update(id, productSpecification)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        ProductSpecification productSpecification = ProductSpecification.builder()
                .id(id)
                .build();
        Mockito.when(productSpecificationRepository.findById(id)).thenReturn(Optional.of(productSpecification));
        Assertions.assertAll(
                () -> productSpecificationService.delete(id)
        );
        Mockito.verify(productSpecificationRepository).delete(productSpecification);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(productSpecificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productSpecificationService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}