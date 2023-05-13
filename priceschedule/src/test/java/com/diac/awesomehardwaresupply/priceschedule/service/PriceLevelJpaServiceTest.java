package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.priceschedule.repository.PriceLevelRepository;
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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        PriceLevelJpaService.class
})
public class PriceLevelJpaServiceTest {

    @Autowired
    private PriceLevelService priceLevelService;

    @MockBean
    private PriceLevelRepository priceLevelRepository;

    @Test
    public void whenFindAll() {
        List<PriceLevel> priceLevels = List.of(
                PriceLevel.builder()
                        .id(1)
                        .build(),
                PriceLevel.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(priceLevelRepository.findAll()).thenReturn(priceLevels);
        assertThat(priceLevelService.findAll()).isEqualTo(priceLevels);
        Mockito.verify(priceLevelRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<PriceLevel> expectedPriceLevels = List.of(
                PriceLevel.builder()
                        .id(1)
                        .build(),
                PriceLevel.builder()
                        .id(2)
                        .build()
        );
        Page<PriceLevel> expectedPriceLevelPage = (Page<PriceLevel>) Mockito.mock(Page.class);
        Mockito.when(expectedPriceLevelPage.getContent()).thenReturn(expectedPriceLevels);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(priceLevelRepository.findAll(pageRequest)).thenReturn(expectedPriceLevelPage);
        assertThat(priceLevelService.getPage(pageRequest).getContent()).isEqualTo(expectedPriceLevels);
        Mockito.verify(priceLevelRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .build();
        Mockito.when(priceLevelRepository.findById(id)).thenReturn(Optional.of(priceLevel));
        assertThat(priceLevelService.findById(id)).isEqualTo(priceLevel);
        Mockito.verify(priceLevelRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(priceLevelRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceLevelService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenFindByNameFound() {
        int id = 1;
        String value = "test";
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .name(value)
                .build();
        Mockito.when(priceLevelRepository.findByName(value)).thenReturn(Optional.of(priceLevel));
        assertThat(priceLevelService.findByName(value)).isEqualTo(priceLevel);
        Mockito.verify(priceLevelRepository).findByName(value);
    }

    @Test
    public void whenFindByNameNotFoundThenThrowException() {
        String value = "test";
        Mockito.when(priceLevelRepository.findByName(value)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceLevelService.findByName(value)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        PriceLevel priceLevel = PriceLevel.builder()
                .name(value)
                .build();
        PriceLevel savedPriceLevel = PriceLevel.builder()
                .id(1)
                .name(value)
                .build();
        Mockito.when(priceLevelRepository.save(priceLevel)).thenReturn(savedPriceLevel);
        assertThat(priceLevelService.add(priceLevel)).isEqualTo(savedPriceLevel);
        Mockito.verify(priceLevelRepository).save(priceLevel);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .name(value)
                .build();
        Mockito.when(priceLevelRepository.findById(id)).thenReturn(Optional.of(priceLevel));
        Mockito.when(priceLevelRepository.save(priceLevel)).thenReturn(priceLevel);
        assertThat(priceLevelService.update(id, priceLevel)).isEqualTo(priceLevel);
        Mockito.verify(priceLevelRepository).save(priceLevel);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .build();
        Mockito.when(priceLevelRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceLevelService.update(id, priceLevel)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .build();
        Mockito.when(priceLevelRepository.findById(id)).thenReturn(Optional.of(priceLevel));
        Assertions.assertAll(
                () -> priceLevelService.delete(id)
        );
        Mockito.verify(priceLevelRepository).delete(priceLevel);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(priceLevelRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceLevelService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}