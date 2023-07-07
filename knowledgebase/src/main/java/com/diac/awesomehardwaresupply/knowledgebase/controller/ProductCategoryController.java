package com.diac.awesomehardwaresupply.knowledgebase.controller;

import com.diac.awesomehardwaresupply.domain.model.ProductCategory;
import com.diac.awesomehardwaresupply.knowledgebase.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, реализующий доступ к объектам модели ProductCategory
 */
@RestController
@AllArgsConstructor
@RequestMapping("/product_category")
public class ProductCategoryController {

    /**
     * Количество категорий на одной странице
     */
    private static final int CATEGORIES_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели ProductCategory
     */
    private final ProductCategoryService productCategoryService;

    /**
     * Получить все категории товаров
     *
     * @param pageNumber Номер страницы
     * @return Страница с категориями
     */
    @GetMapping("")
    public ResponseEntity<Page<ProductCategory>> index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        return new ResponseEntity<>(
                productCategoryService.getPage(PageRequest.of(pageNumber - 1, CATEGORIES_PER_PAGE)),
                HttpStatus.OK
        );
    }

    /**
     * Получить категорию товаров по ID
     *
     * @param id Идентификатор категории
     * @return Ответ с категорией и статусом Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                productCategoryService.findById(id),
                HttpStatus.OK
        );
    }

    /**
     * Добавить новую категорию товаров в систему
     *
     * @param productCategory Новая категория товаров
     * @return Ответ с созданной категорией товаров
     */
    @PostMapping("")
    public ResponseEntity<ProductCategory> post(@RequestBody @Valid ProductCategory productCategory) {
        return new ResponseEntity<>(
                productCategoryService.add(productCategory),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные категории товаров
     *
     * @param id              Идентификатор категории товаров
     * @param productCategory Объект с новыми данными категории товаров
     * @return Ответ с обновленной категорией товаров
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> put(
            @PathVariable("id") int id, @RequestBody @Valid ProductCategory productCategory
    ) {
        return new ResponseEntity<>(
                productCategoryService.update(id, productCategory),
                HttpStatus.OK
        );
    }

    /**
     * Удалить категорию товаров из системы
     *
     * @param id Идентификатор категории товаров
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        productCategoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}