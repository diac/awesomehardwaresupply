package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.domain.model.PriceCodePricing;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.priceschedule.repository.PriceCodePricingRepository;
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
        PriceCodePricingJpaService.class
})
public class PriceCodePricingJpaServiceTest {

    @Autowired
    private PriceCodePricingService priceCodePricingService;

    @MockBean
    private PriceCodePricingRepository priceCodePricingRepository;

    @Test
    public void whenFindAll() {
        List<PriceCodePricing> priceCodePricings = List.of(
                PriceCodePricing.builder()
                        .id(1)
                        .build(),
                PriceCodePricing.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(priceCodePricingRepository.findAll()).thenReturn(priceCodePricings);
        assertThat(priceCodePricingService.findAll()).isEqualTo(priceCodePricings);
        Mockito.verify(priceCodePricingRepository).findAll();
    }

    @Test
    public void whenGetPage() {
        List<PriceCodePricing> expectedPriceCodePricings = List.of(
                PriceCodePricing.builder()
                        .id(1)
                        .build(),
                PriceCodePricing.builder()
                        .id(2)
                        .build()
        );
        Page<PriceCodePricing> expectedPriceCodePricingPage = (Page<PriceCodePricing>) Mockito.mock(Page.class);
        Mockito.when(expectedPriceCodePricingPage.getContent()).thenReturn(expectedPriceCodePricings);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(priceCodePricingRepository.findAll(pageRequest)).thenReturn(expectedPriceCodePricingPage);
        assertThat(priceCodePricingService.getPage(pageRequest).getContent()).isEqualTo(expectedPriceCodePricings);
        Mockito.verify(priceCodePricingRepository).findAll(pageRequest);
    }

    @Test
    public void whenFindByIdFound() {
        int id = 1;
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .id(id)
                .build();
        Mockito.when(priceCodePricingRepository.findById(id)).thenReturn(Optional.of(priceCodePricing));
        assertThat(priceCodePricingService.findById(id)).isEqualTo(priceCodePricing);
        Mockito.verify(priceCodePricingRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(priceCodePricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceCodePricingService.findById(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenAdd() {
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .priceCode(PriceCode.builder().id(1).build())
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        PriceCodePricing savedPriceCodePricing = PriceCodePricing.builder()
                .id(1)
                .priceCode(PriceCode.builder().id(1).build())
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        Mockito.when(priceCodePricingRepository.save(priceCodePricing)).thenReturn(savedPriceCodePricing);
        assertThat(priceCodePricingService.add(priceCodePricing)).isEqualTo(savedPriceCodePricing);
        Mockito.verify(priceCodePricingRepository).save(priceCodePricing);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .id(id)
                .priceCode(PriceCode.builder().id(1).build())
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        Mockito.when(priceCodePricingRepository.findById(id)).thenReturn(Optional.of(priceCodePricing));
        Mockito.when(priceCodePricingRepository.save(priceCodePricing)).thenReturn(priceCodePricing);
        assertThat(priceCodePricingService.update(id, priceCodePricing)).isEqualTo(priceCodePricing);
        Mockito.verify(priceCodePricingRepository).save(priceCodePricing);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .id(id)
                .build();
        Mockito.when(priceCodePricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceCodePricingService.update(id, priceCodePricing)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .id(id)
                .build();
        Mockito.when(priceCodePricingRepository.findById(id)).thenReturn(Optional.of(priceCodePricing));
        Assertions.assertAll(
                () -> priceCodePricingService.delete(id)
        );
        Mockito.verify(priceCodePricingRepository).delete(priceCodePricing);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(priceCodePricingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceCodePricingService.delete(id)
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}