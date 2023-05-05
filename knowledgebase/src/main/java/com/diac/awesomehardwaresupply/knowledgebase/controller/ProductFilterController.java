package com.diac.awesomehardwaresupply.knowledgebase.controller;

import com.diac.awesomehardwaresupply.domain.model.ProductFilter;
import com.diac.awesomehardwaresupply.knowledgebase.service.ProductFilterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, реализующий доступ к объектам модели ProductFilter
 */
@RestController
@AllArgsConstructor
@RequestMapping("/product_filter")
public class ProductFilterController {

    /**
     * Количество фильтров на одной странице
     */
    private static final int PRODUCT_FILTERS_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели ProductFilter
     */
    private final ProductFilterService productFilterService;

    /**
     * Получить все фильтры
     *
     * @param pageNumber Номер страницы
     * @return Страница с фильтрами
     */
    @GetMapping("")
    public ResponseEntity<Page<ProductFilter>> index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        return new ResponseEntity<>(
                productFilterService.getPage(PageRequest.of(pageNumber - 1, PRODUCT_FILTERS_PER_PAGE)),
                HttpStatus.OK
        );
    }

    /**
     * Получить фильтр товара по ID
     *
     * @param id Идентификатор фильтра
     * @return Ответ с фильтром и статусом FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductFilter> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                productFilterService.findById(id),
                HttpStatus.FOUND
        );
    }

    /**
     * Добавить новый фильтр товаров в систему
     *
     * @param productFilter Новый фильтр
     * @return Ответ с созданным фильтром
     */
    @PostMapping("")
    public ResponseEntity<ProductFilter> post(@RequestBody @Valid ProductFilter productFilter) {
        return new ResponseEntity<>(
                productFilterService.add(productFilter),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные фильтра товаров
     *
     * @param id Идентификатор фильтра
     * @param productFilter Объект с обновленными данными фильтра
     * @return Ответ с обновленным фильтром
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductFilter> put(
            @PathVariable("id") int id,
            @RequestBody @Valid ProductFilter productFilter
    ) {
        return new ResponseEntity<>(
                productFilterService.update(id, productFilter),
                HttpStatus.OK
        );
    }

    /**
     * Удалить фильтр товаров из системы
     *
     * @param id Идентификатор фильтра
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        productFilterService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}