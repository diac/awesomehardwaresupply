package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
import com.diac.awesomehardwaresupply.priceschedule.service.CustomerPricingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, реализующий доступ к объектам модели CustomerPricing
 */
@RestController
@AllArgsConstructor
@RequestMapping("/customer_pricing")
public class CustomerPricingController {

    /**
     * Количество правил на странице
     */
    private static final int CUSTOMER_PRICINGS_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели CustomerPricing
     */
    private final CustomerPricingService customerPricingService;

    /**
     * Получить все правила
     *
     * @param pageNumber Номер страницы
     * @return Страница с правилами
     */
    @GetMapping("")
    public ResponseEntity<Page<CustomerPricing>> index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        return new ResponseEntity<>(
                customerPricingService.getPage(PageRequest.of(pageNumber - 1, CUSTOMER_PRICINGS_PER_PAGE)),
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
    public ResponseEntity<CustomerPricing> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                customerPricingService.findById(id),
                HttpStatus.FOUND
        );
    }

    /**
     * Добавить новое правило в систему
     *
     * @param customerPricing Новое правило
     * @return Ответ с созданным правилом
     */
    @PostMapping("")
    public ResponseEntity<CustomerPricing> post(@RequestBody @Valid CustomerPricing customerPricing) {
        return new ResponseEntity<>(
                customerPricingService.add(customerPricing),
                HttpStatus.CREATED
        );
    }


    /**
     * Обновить данные правила
     *
     * @param id              Идентификатор правила
     * @param customerPricing Объект с новыми данными правила
     * @return Ответ с обновленным правилом
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerPricing> put(
            @PathVariable("id") int id, @RequestBody @Valid CustomerPricing customerPricing
    ) {
        return new ResponseEntity<>(
                customerPricingService.update(id, customerPricing),
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
        customerPricingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}