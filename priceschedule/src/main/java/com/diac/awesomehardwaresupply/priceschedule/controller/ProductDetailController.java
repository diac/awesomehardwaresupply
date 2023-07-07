package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import com.diac.awesomehardwaresupply.priceschedule.service.ProductDetailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, реализующий доступ к объектам ProductDetail
 */
@RestController
@AllArgsConstructor
@RequestMapping("/product_detail")
public class ProductDetailController {

    /**
     * Количество деталей товаров на странице
     */
    private static final int PRODUCT_DETAILS_PER_PAGE = 10;

    /**
     * Сервис для работы с объектами модели ProductDetail
     */
    private final ProductDetailService productDetailService;

    /**
     * Получить все подробности товаров
     *
     * @param pageName Номер страницы
     * @return Страница с подробностями товаров
     */
    @GetMapping("")
    public ResponseEntity<Page<ProductDetail>> index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageName
    ) {
        return new ResponseEntity<>(
                productDetailService.getPage(PageRequest.of(pageName - 1, PRODUCT_DETAILS_PER_PAGE)),
                HttpStatus.OK
        );
    }

    /**
     * Получить подробности товара по ID
     *
     * @param id Идентификатор подробностей товара
     * @return Ответ с подробностями товара и статусом Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetail> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                productDetailService.findById(id),
                HttpStatus.OK
        );
    }

    /**
     * Добавить новые подробности товара в систему
     *
     * @param productDetail Новые подробности товара
     * @return Ответ с созданными подробностями
     */
    @PostMapping("")
    public ResponseEntity<ProductDetail> post(@RequestBody @Valid ProductDetail productDetail) {
        return new ResponseEntity<>(
                productDetailService.add(productDetail),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные подробностей товара
     *
     * @param id            Идентификатор подробностей товара
     * @param productDetail Объект с новыми данными подробностей товара
     * @return Ответ с обновленными подробностями товара
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDetail> put(
            @PathVariable("id") int id, @RequestBody @Valid ProductDetail productDetail
    ) {
        return new ResponseEntity<>(
                productDetailService.update(id, productDetail),
                HttpStatus.OK
        );
    }

    /**
     * Удалить подробности товара из системы
     *
     * @param id Идентификатор подробностей товара
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        productDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}