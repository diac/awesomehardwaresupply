package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.model.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductFilter
 */
public interface ProductFilterService {

    /**
     * Найти все фильтры товаров
     *
     * @return Список фильтров товаров
     */
    List<ProductFilter> findAll();

    /**
     * Получить страницу с фильтрами товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с фильтрами товаров
     */
    Page<ProductFilter> getPage(PageRequest pageRequest);

    /**
     * Найти фильтр товаров по ID
     *
     * @param id Идентификатор фильтра товаров
     * @return Фильтр товаров
     */
    ProductFilter findById(int id);

    /**
     * Добавить новый фильтр товаров в систему
     *
     * @param productFilter Новый фильтр товаров
     * @return Сохраненный фильтр товаров
     */
    ProductFilter add(ProductFilter productFilter);

    /**
     * Обновить данный фильтра товаров в системе
     *
     * @param id            Идентификатор фильтра товаров, данные которого необходимо обновить
     * @param productFilter Объект с обновленными данными фильтра товаров
     * @return Обновленный фильтр товаров
     */
    ProductFilter update(int id, ProductFilter productFilter);

    /**
     * Удалить фильтр товаров из системы
     *
     * @param id Идентификатор фильтра товаров
     */
    void delete(int id);
}