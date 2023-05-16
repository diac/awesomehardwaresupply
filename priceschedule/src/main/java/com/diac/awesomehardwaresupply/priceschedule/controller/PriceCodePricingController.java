package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.model.PriceCodePricing;
import com.diac.awesomehardwaresupply.priceschedule.service.PriceCodePricingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, реализующий доступ к объектам модели PriceCodePricing
 */
@RestController
@AllArgsConstructor
@RequestMapping("/price_code_pricing")
public class PriceCodePricingController {

    /**
     * Количество правил на странице
     */
    private static final int PRICE_CODE_PRICINGS_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели PriceCodePricing
     */
    private final PriceCodePricingService priceCodePricingService;

    /**
     * Получить все правила
     *
     * @param pageNumber Номер страницы
     * @return Страница с правилами
     */
    @GetMapping
    public ResponseEntity<Page<PriceCodePricing>> index(
            @RequestParam(name = "param", required = false, defaultValue = "1") int pageNumber
    ) {
        return new ResponseEntity<>(
                priceCodePricingService.getPage(PageRequest.of(pageNumber - 1, PRICE_CODE_PRICINGS_PER_PAGE)),
                HttpStatus.OK
        );
    }

    /**
     * Получить правило по ID
     *
     * @param id Идентификатор правила
     * @return Ответ с правилом и статусом Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriceCodePricing> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                priceCodePricingService.findById(id),
                HttpStatus.FOUND
        );
    }

    /**
     * Добавить новое правило в систему
     *
     * @param priceCodePricing Новое правило
     * @return Ответ с созданным правилом
     */
    @PostMapping("")
    public ResponseEntity<PriceCodePricing> post(@RequestBody @Valid PriceCodePricing priceCodePricing) {
        return new ResponseEntity<>(
                priceCodePricingService.add(priceCodePricing),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные правила
     *
     * @param id               Идентификатор правила
     * @param priceCodePricing Объект с новыми данными правила
     * @return Ответ с обновленным правилом
     */
    @PutMapping("/{id}")
    public ResponseEntity<PriceCodePricing> put(
            @PathVariable("id") int id, @RequestBody @Valid PriceCodePricing priceCodePricing
    ) {
        return new ResponseEntity<>(
                priceCodePricingService.update(id, priceCodePricing),
                HttpStatus.OK
        );
    }

    /**
     * Удалить правило из системы
     *
     * @param id Идентификатор правила, которое необходимо удалить
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        priceCodePricingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}