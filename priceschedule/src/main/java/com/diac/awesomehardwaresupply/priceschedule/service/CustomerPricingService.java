package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели CustomerPricing
 */
public interface CustomerPricingService {

    /**
     * Найти все правила
     *
     * @return Список правил
     */
    List<CustomerPricing> findAll();

    /**
     * Получить страницу с правилами
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с правилами
     */
    Page<CustomerPricing> getPage(PageRequest pageRequest);

    /**
     * Найти правило по ID
     *
     * @param id Идентификатор правила
     * @return Правило
     */
    CustomerPricing findById(int id);

    /**
     * Добавить новое правило в систему
     *
     * @param customerPricing Новое правило
     * @return Сохраненное правило
     */
    CustomerPricing add(CustomerPricing customerPricing);

    /**
     * Обновить данные правила в системе
     *
     * @param id              Идентификатор правила, данные которого необходимо обновить
     * @param customerPricing Объект с обновленными данными правила
     * @return Обновленное правило
     */
    CustomerPricing update(int id, CustomerPricing customerPricing);

    /**
     * Удалить правило из системы
     *
     * @param id Идентификатор правила, которое необходимо удалить
     */
    void delete(int id);
}