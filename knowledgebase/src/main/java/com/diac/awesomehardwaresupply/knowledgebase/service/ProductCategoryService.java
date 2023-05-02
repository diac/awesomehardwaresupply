package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductCategory
 */
public interface ProductCategoryService {

    /**
     * Найти все категории товаров
     *
     * @return Список категорий товаров
     */
    List<ProductCategory> findAll();

    /**
     * Получить страницу с категориями товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с категориями товаров
     */
    Page<ProductCategory> getPage(PageRequest pageRequest);

    /**
     * Найти категорию товаров по ID
     *
     * @param id Идентификатор категории товаров
     * @return Категория товаров
     */
    ProductCategory findById(int id);

    /**
     * Добавить новую категорию товаров в систему
     *
     * @param productCategory Новая категория товаров
     * @return Сохраненная категория товаров
     */
    ProductCategory add(ProductCategory productCategory);

    /**
     * Обновить данные категории товаров в системе
     *
     * @param id              Идентификатор категории товаров, данные которой необходимо обновить
     * @param productCategory Объект с обновленными данными категории товаров
     * @return Обновленная категория товаров
     */
    ProductCategory update(int id, ProductCategory productCategory);

    /**
     * Удалить категорию товаров из системы
     *
     * @param id Идентификатор категории товаров, которую необходимо удалить
     */
    void delete(int id);
}