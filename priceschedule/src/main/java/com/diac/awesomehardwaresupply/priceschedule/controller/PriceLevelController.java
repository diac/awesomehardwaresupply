package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.priceschedule.service.PriceLevelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели PriceLevel
 */
@RestController
@AllArgsConstructor
@RequestMapping("/price_level")
public class PriceLevelController {

    /**
     * Количество уровней цены на странице
     */
    private static final int PRICE_LEVELS_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели PriceLevel
     */
    private final PriceLevelService priceLevelService;

    /**
     * Получить все уровни цен
     *
     * @param pageNumber Номер страницы
     * @return Страница с уровнями цен
     */
    @GetMapping("")
    public ResponseEntity<Page<PriceLevel>> index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        return new ResponseEntity<>(
                priceLevelService.getPage(PageRequest.of(pageNumber - 1, PRICE_LEVELS_PER_PAGE)),
                HttpStatus.OK
        );
    }

    /**
     * Получить все коды цен без разбиения на страницы
     *
     * @return Ответ со списком уровней цен
     */
    @GetMapping("/all")
    public ResponseEntity<List<PriceLevel>> all() {
        return new ResponseEntity<>(
                priceLevelService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить уровень цены по ID
     *
     * @param id Идентификатор уровня цены
     * @return Ответ с уровнем цены и статусом Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriceLevel> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                priceLevelService.findById(id),
                HttpStatus.OK
        );
    }

    /**
     * Добавить новый уровень цены в систему
     *
     * @param priceLevel Новый уровень цены
     * @return Ответ с созданным уровнем цены
     */
    @PostMapping("")
    public ResponseEntity<PriceLevel> post(@RequestBody @Valid PriceLevel priceLevel) {
        return new ResponseEntity<>(
                priceLevelService.add(priceLevel),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные уровня цены
     *
     * @param id         Идентификатор уровня цены
     * @param priceLevel Объект с новыми данными уровня цены
     * @return Ответ с обновленным уровнем цены
     */
    @PutMapping("/{id}")
    public ResponseEntity<PriceLevel> put(
            @PathVariable("id") int id, @RequestBody @Valid PriceLevel priceLevel
    ) {
        return new ResponseEntity<>(
                priceLevelService.update(id, priceLevel),
                HttpStatus.OK
        );
    }

    /**
     * Удалить уровень цены из системы
     *
     * @param id Идентификатор уровня цены, который необходимо удалить
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        priceLevelService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}