package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductDetail
 */
public interface ProductDetailService {

    /**
     * Найти все подробности товаров
     *
     * @return Список с подробностями товаров
     */
    List<ProductDetail> findAll();

    /**
     * Получить страницу с подробностями товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с подробностями товаров
     */
    Page<ProductDetail> getPage(PageRequest pageRequest);

    /**
     * Найти подробности товара по ID
     *
     * @param id Идентификатор подробностей товара
     * @return Подробности товара
     */
    ProductDetail findById(int id);

    /**
     * Найти подробности товара по артикулу товара
     *
     * @param productSku Артикул товара
     * @return Подробности товара
     */
    ProductDetail findByProductSku(String productSku);

    /**
     * Добавить новые подробности товара в систему
     *
     * @param productDetail Подробности товара
     * @return Сохраненные подробности товара
     */
    ProductDetail add(ProductDetail productDetail);

    /**
     * Обновить подробности товара в системе
     *
     * @param id            Идентификатор подробностей товара
     * @param productDetail Объект с обновленными данными подробностей товара
     * @return Обновленные подробности товара
     */
    ProductDetail update(int id, ProductDetail productDetail);

    /**
     * Удалить подробности товара из системы
     *
     * @param id Идентификатор подробностей, которые необходимо удалить
     */
    void delete(int id);
}