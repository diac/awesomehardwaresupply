package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.priceschedule.service.PriceCodeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели PriceCode
 */
@RestController
@AllArgsConstructor
@RequestMapping("/price_code")
public class PriceCodeController {

    /**
     * Количество кодов цены на странице
     */
    private static final int PRICE_CODES_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели PriceCode
     */
    private final PriceCodeService priceCodeService;

    /**
     * Получить все коды цен
     *
     * @param pageNumber Номер страницы
     * @return Страница с кодами цен
     */
    @GetMapping("")
    public ResponseEntity<Page<PriceCode>> index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        return new ResponseEntity<>(
                priceCodeService.getPage(PageRequest.of(pageNumber - 1, PRICE_CODES_PER_PAGE)),
                HttpStatus.OK
        );
    }

    /**
     * Получить все коды цен без разбиения на страницы
     *
     * @return Ответ со списком кодов цен
     */
    @GetMapping("/all")
    public ResponseEntity<List<PriceCode>> all() {
        return new ResponseEntity<>(
                priceCodeService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить код цены по ID
     *
     * @param id Идентификатор кода цены
     * @return Ответ с кодом цены и статусом Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriceCode> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                priceCodeService.findById(id),
                HttpStatus.OK
        );
    }

    /**
     * Добавить новый код цены в систему
     *
     * @param priceCode Новый код цен
     * @return Ответ с созданным кодом цен
     */
    @PostMapping("")
    public ResponseEntity<PriceCode> post(@RequestBody @Valid PriceCode priceCode) {
        return new ResponseEntity<>(
                priceCodeService.add(priceCode),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные кода цены
     *
     * @param id        Идентификатор кода цены
     * @param priceCode Объект с новыми данными кода цены
     * @return Ответ с обновленным кодом цены
     */
    @PutMapping("/{id}")
    public ResponseEntity<PriceCode> put(
            @PathVariable("id") int id, @RequestBody @Valid PriceCode priceCode
    ) {
        return new ResponseEntity<>(
                priceCodeService.update(id, priceCode),
                HttpStatus.OK
        );
    }

    /**
     * Удалить код цены из системы
     *
     * @param id Идентификатор кода цены, который необходимо удалить
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        priceCodeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}