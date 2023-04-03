package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.model.Product;

import java.util.List;

/**
 * Сервис для работы с объектами модели Product
 */
public interface ProductService {

    /**
     * Найти все товары
     *
     * @return Список товаров
     */
    List<Product> findAll();

    /**
     * Найти товар по ID
     *
     * @param id Идентификатор товара
     * @return Товар
     */
    Product findById(int id);

    /**
     * Найти товар по артикулу
     *
     * @param sku Артикул
     * @return Товар
     */
    Product findBySku(String sku);

    /**
     * Добавить новый товар в систему
     *
     * @param product Новый товар
     * @return Сохраненный товар
     */
    Product add(Product product);

    /**
     * Обновить данные товара в системе
     *
     * @param id      Идентификатор товара, данные которого необходимо обновить
     * @param product Объект с обновленными данными товара
     * @return Обновленный товар
     */
    Product update(int id, Product product);

    /**
     * Удалить товар из системы
     *
     * @param id Идентификатор товара, который необходимо удалить
     */
    void delete(int id);
}