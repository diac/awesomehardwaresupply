package com.diac.awesomehardwaresupply.knowledgebase.controller;

import com.diac.awesomehardwaresupply.domain.model.Product;
import com.diac.awesomehardwaresupply.knowledgebase.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели Product
 */
@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    /**
     * Сервис для работы с объектами модели Product
     */
    private final ProductService productService;

    /**
     * Получить все товары
     *
     * @return Список товаров
     */
    @GetMapping("")
    public ResponseEntity<List<Product>> index() {
        return new ResponseEntity<>(
                productService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить товар по ID
     *
     * @param id Идентификатор товара
     * @return Ответ с товаром и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                productService.findById(id),
                HttpStatus.FOUND
        );
    }

    /**
     * Получить товар по артикулу
     *
     * @param sku Артикул
     * @return Ответ с товаром и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<Product> getBySku(@PathVariable("sku") String sku) {
        return new ResponseEntity<>(
                productService.findBySku(sku),
                HttpStatus.FOUND
        );
    }

    /**
     * Добавить новый товар в систему
     *
     * @param product Новый товар
     * @return Ответ с созданым товаром
     */
    @PostMapping("")
    public ResponseEntity<Product> post(@RequestBody @Valid Product product) {
        return new ResponseEntity<>(
                productService.add(product),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные товара
     *
     * @param id      Идентификатор товара
     * @param product Объект с новыми данными товара
     * @return Ответ с обновленным товаром
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> put(@PathVariable("id") int id, @RequestBody @Valid Product product) {
        return new ResponseEntity<>(
                productService.update(id, product),
                HttpStatus.OK
        );
    }

    /**
     * Удалить товар из системы
     *
     * @param id Идентификатор товара
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}