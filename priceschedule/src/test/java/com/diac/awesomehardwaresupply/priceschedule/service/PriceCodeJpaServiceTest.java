package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.priceschedule.repository.PriceCodeRepository;
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
        PriceCodeJpaService.class
})
public class PriceCodeJpaServiceTest {

    @Autowired
    private PriceCodeService priceCodeService;

    @MockBean
    private PriceCodeRepository priceCodeRepository;

    @Test
    public void whenFindAll() {
        List<PriceCode> priceCodes = List.of(
                PriceCode.builder()
                        .id(1)
                        .build(),
                PriceCode.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(priceCodeRepository.findAll()).thenReturn(priceCodes);
        assertThat(priceCodeService.findAll()).isEqualTo(priceCodes);
        Mockito.verify(priceCodeRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<PriceCode> expectedPriceCodes = List.of(
                PriceCode.builder()
                        .id(1)
                        .build(),
                PriceCode.builder()
                        .id(2)
                        .build()
        );
        Page<PriceCode> expectedPriceCodePage = (Page<PriceCode>) Mockito.mock(Page.class);
        Mockito.when(expectedPriceCodePage.getContent()).thenReturn(expectedPriceCodes);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(priceCodeRepository.findAll(pageRequest)).thenReturn(expectedPriceCodePage);
        assertThat(priceCodeService.getPage(pageRequest).getContent()).isEqualTo(expectedPriceCodes);
        Mockito.verify(priceCodeRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        PriceCode priceCode = PriceCode.builder()
                .id(id)
                .build();
        Mockito.when(priceCodeRepository.findById(id)).thenReturn(Optional.of(priceCode));
        assertThat(priceCodeService.findById(id)).isEqualTo(priceCode);
        Mockito.verify(priceCodeRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(priceCodeRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceCodeService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenFindByNameFound() {
        int id = 1;
        String value = "test";
        PriceCode priceCode = PriceCode.builder()
                .id(id)
                .name(value)
                .build();
        Mockito.when(priceCodeRepository.findByName(value)).thenReturn(Optional.of(priceCode));
        assertThat(priceCodeService.findByName(value)).isEqualTo(priceCode);
        Mockito.verify(priceCodeRepository).findByName(value);
    }

    @Test
    public void whenFindByNameNotFoundThenThrowException() {
        String value = "test";
        Mockito.when(priceCodeRepository.findByName(value)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceCodeService.findByName(value)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        String value = "test";
        PriceCode priceCode = PriceCode.builder()
                .name(value)
                .build();
        PriceCode savedPriceCode = PriceCode.builder()
                .id(1)
                .name(value)
                .build();
        Mockito.when(priceCodeRepository.save(priceCode)).thenReturn(savedPriceCode);
        assertThat(priceCodeService.add(priceCode)).isEqualTo(savedPriceCode);
        Mockito.verify(priceCodeRepository).save(priceCode);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        String value = "test";
        PriceCode priceCode = PriceCode.builder()
                .id(id)
                .name(value)
                .build();
        Mockito.when(priceCodeRepository.findById(id)).thenReturn(Optional.of(priceCode));
        Mockito.when(priceCodeRepository.save(priceCode)).thenReturn(priceCode);
        assertThat(priceCodeService.update(id, priceCode)).isEqualTo(priceCode);
        Mockito.verify(priceCodeRepository).save(priceCode);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        PriceCode priceCode = PriceCode.builder()
                .id(id)
                .build();
        Mockito.when(priceCodeRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceCodeService.update(id, priceCode)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        PriceCode priceCode = PriceCode.builder()
                .id(id)
                .build();
        Mockito.when(priceCodeRepository.findById(id)).thenReturn(Optional.of(priceCode));
        Assertions.assertAll(
                () -> priceCodeService.delete(id)
        );
        Mockito.verify(priceCodeRepository).delete(priceCode);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(priceCodeRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceCodeService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}