package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.model.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductSpecification
 */
public interface ProductSpecificationService {

    /**
     * Найти все спецификации товаров
     *
     * @return Список спецификаций товаров
     */
    List<ProductSpecification> findAll();

    /**
     * Получить страницу со спецификациями товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница со спецификациями товаров
     */
    Page<ProductSpecification> getPage(PageRequest pageRequest);

    /**
     * Найти спецификацию товаров по ID
     *
     * @param id Идентификатор спецификации товаров
     * @return Спецификация товаров
     */
    ProductSpecification findById(int id);

    /**
     * Добавить новую спецификацию товаров в систему
     *
     * @param productSpecification Новая спецификация товаров
     * @return Сохраненная спецификация товаров
     */
    ProductSpecification add(ProductSpecification productSpecification);

    /**
     * Обновить данные спецификации товаров в системе
     *
     * @param id                   Идентификатор спецификации товаров, данные которой необходимо обновить
     * @param productSpecification Объект с обновленными данными спецификации товаров
     * @return Обновленная спецификация товаров
     */
    ProductSpecification update(int id, ProductSpecification productSpecification);

    /**
     * Удалить спецификацию товаров из системы
     *
     * @param id Идентификатор спецификации товаров, которую необходимо удалить
     */
    void delete(int id);
}