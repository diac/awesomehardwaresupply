package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
import com.diac.awesomehardwaresupply.priceschedule.service.ProductPricingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, реализующий доступ к объектам модели ProductPricing
 */
@RestController
@AllArgsConstructor
@RequestMapping("/product_pricing")
public class ProductPricingController {

    /**
     * Количество правил на странице
     */
    private static final int PRODUCT_PRICINGS_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели ProductPricing
     */
    private final ProductPricingService productPricingService;

    /**
     * Получить все правила
     *
     * @param pageNumber Номер страницы
     * @return Страница с правилами
     */
    @GetMapping("")
    public ResponseEntity<Page<ProductPricing>> index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        return new ResponseEntity<>(
                productPricingService.getPage(PageRequest.of(pageNumber - 1, PRODUCT_PRICINGS_PER_PAGE)),
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
    public ResponseEntity<ProductPricing> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                productPricingService.findById(id),
                HttpStatus.OK
        );
    }

    /**
     * Добавить новое правило в систему
     *
     * @param productPricing Новое правило
     * @return Ответ с созданным правилом
     */
    @PostMapping("")
    public ResponseEntity<ProductPricing> post(@RequestBody @Valid ProductPricing productPricing) {
        return new ResponseEntity<>(
                productPricingService.add(productPricing),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные правила
     *
     * @param id             Идентификатор правила
     * @param productPricing Объект с новыми данными правила
     * @return Ответ с обновленным правилом
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductPricing> put(
            @PathVariable("id") int id, @RequestBody @Valid ProductPricing productPricing
    ) {
        return new ResponseEntity<>(
                productPricingService.update(id, productPricing),
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
        productPricingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}