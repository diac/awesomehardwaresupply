package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductPricing
 */
public interface ProductPricingService {

    /**
     * Найти все правила
     *
     * @return Список правил
     */
    List<ProductPricing> findAll();

    /**
     * Получить страницу с правилами
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с правилами
     */
    Page<ProductPricing> getPage(PageRequest pageRequest);

    /**
     * Найти правило по ID
     *
     * @param id Идентификатор правила
     * @return Правило
     */
    ProductPricing findById(int id);

    /**
     * Добавить новое правило в систему
     *
     * @param productPricing Новое правило
     * @return Сохраненное правило
     */
    ProductPricing add(ProductPricing productPricing);

    /**
     * Обновить данные правила в системе
     *
     * @param id             Идентификатор правила, данные которого необходимо обновить
     * @param productPricing Объект с обновленными данными правила
     * @return Обновленное правило
     */
    ProductPricing update(int id, ProductPricing productPricing);

    /**
     * Удалить правило из системы
     *
     * @param id Идентификатор правила, которое необходимо удалить
     */
    void delete(int id);
}