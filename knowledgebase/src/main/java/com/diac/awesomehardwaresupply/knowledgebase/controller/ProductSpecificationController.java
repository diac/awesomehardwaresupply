package com.diac.awesomehardwaresupply.knowledgebase.controller;

import com.diac.awesomehardwaresupply.domain.model.ProductSpecification;
import com.diac.awesomehardwaresupply.knowledgebase.service.ProductSpecificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, реализующий доступ к объектам модели ProductSpecification
 */
@RestController
@AllArgsConstructor
@RequestMapping("/product_specification")
public class ProductSpecificationController {

    /**
     * Количество спецификаций на одной странице
     */
    private static final int PRODUCT_SPECIFICATIONS_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели ProductSpecification
     */
    private final ProductSpecificationService productSpecificationService;

    /**
     * Получить все спецификации
     *
     * @param pageNumber Номер страницы
     * @return Страница со спецификациями
     */
    @GetMapping("")
    public ResponseEntity<Page<ProductSpecification>> index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        return new ResponseEntity<>(
                productSpecificationService.getPage(PageRequest.of(pageNumber - 1, PRODUCT_SPECIFICATIONS_PER_PAGE)),
                HttpStatus.OK
        );
    }

    /**
     * Получить спецификацию товаров по ID
     *
     * @param id Идентификатор спецификации
     * @return Ответ со спецификацией и статусом FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductSpecification> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                productSpecificationService.findById(id),
                HttpStatus.OK
        );
    }

    /**
     * Добавить новую спецификацию товаров в систему
     *
     * @param productSpecification Новая спецификация
     * @return Ответ с созданной спецификацией
     */
    @PostMapping("")
    public ResponseEntity<ProductSpecification> post(@RequestBody @Valid ProductSpecification productSpecification) {
        return new ResponseEntity<>(
                productSpecificationService.add(productSpecification),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные спецификации товаров
     *
     * @param id                   Идентификатор спецификации
     * @param productSpecification Объект с обновленными данными спецификации
     * @return Ответ с обновленной спецификацией
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductSpecification> put(
            @PathVariable("id") int id,
            @RequestBody @Valid ProductSpecification productSpecification
    ) {
        return new ResponseEntity<>(
                productSpecificationService.update(id, productSpecification),
                HttpStatus.OK
        );
    }

    /**
     * Удалить спецификацию товаров из системы
     *
     * @param id Идентификатор спецификации
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        productSpecificationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}