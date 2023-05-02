package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductCategory;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductCategoryRepository;
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
        ProductCategoryJpaService.class
})
public class ProductCategoryJpaServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void whenFindAll() {
        List<ProductCategory> productCategories = List.of(
                ProductCategory.builder()
                        .id(1)
                        .build(),
                ProductCategory.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(productCategoryRepository.findAll()).thenReturn(productCategories);
        assertThat(productCategoryService.findAll()).isEqualTo(productCategories);
        Mockito.verify(productCategoryRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<ProductCategory> expectedProductCategories = List.of(
                ProductCategory.builder()
                        .id(1)
                        .build(),
                ProductCategory.builder()
                        .id(2)
                        .build()
        );
        Page<ProductCategory> expectedProductCategoryPage = Mockito.mock(Page.class);
        Mockito.when(expectedProductCategoryPage.getContent()).thenReturn(expectedProductCategories);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(productCategoryRepository.findAll(pageRequest)).thenReturn(expectedProductCategoryPage);
        assertThat(productCategoryService.getPage(pageRequest).getContent()).isEqualTo(expectedProductCategories);
        Mockito.verify(productCategoryRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        ProductCategory productCategory = ProductCategory.builder()
                .id(id)
                .build();
        Mockito.when(productCategoryRepository.findById(id)).thenReturn(Optional.of(productCategory));
        assertThat(productCategoryService.findById(id)).isEqualTo(productCategory);
        Mockito.verify(productCategoryRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(productCategoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productCategoryService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        ProductCategory productCategory = ProductCategory.builder()
                .name(value)
                .description(value)
                .build();
        ProductCategory savedProductCategory = ProductCategory.builder()
                .id(1)
                .name(value)
                .description(value)
                .build();
        Mockito.when(productCategoryRepository.save(productCategory)).thenReturn(savedProductCategory);
        assertThat(productCategoryService.add(productCategory)).isEqualTo(savedProductCategory);
        Mockito.verify(productCategoryRepository).save(productCategory);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        ProductCategory productCategory = ProductCategory.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        Mockito.when(productCategoryRepository.findById(id)).thenReturn(Optional.of(productCategory));
        Mockito.when(productCategoryRepository.save(productCategory)).thenReturn(productCategory);
        assertThat(productCategoryService.update(id, productCategory)).isEqualTo(productCategory);
        Mockito.verify(productCategoryRepository).save(productCategory);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        ProductCategory productCategory = ProductCategory.builder()
                .id(id)
                .build();
        Mockito.when(productCategoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productCategoryService.update(id, productCategory)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        ProductCategory productCategory = ProductCategory.builder()
                .id(id)
                .build();
        Mockito.when(productCategoryRepository.findById(id)).thenReturn(Optional.of(productCategory));
        Assertions.assertAll(
                () -> productCategoryService.delete(id)
        );
        Mockito.verify(productCategoryRepository).delete(productCategory);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(productCategoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> productCategoryService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}