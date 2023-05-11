package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.model.PriceCodePricing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели PriceCodePricing
 */
public interface PriceCodePricingService {

    /**
     * Найти все правила
     *
     * @return Список правил
     */
    List<PriceCodePricing> findAll();

    /**
     * Получить страницу с правилами
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с правилами
     */
    Page<PriceCodePricing> getPage(PageRequest pageRequest);

    /**
     * Найти правило по ID
     *
     * @param id Идентификатор правила
     * @return Правило
     */
    PriceCodePricing findById(int id);

    /**
     * Добавить новое правило в систему
     *
     * @param priceCodePricing Новое правило
     * @return Сохраненное правило
     */
    PriceCodePricing add(PriceCodePricing priceCodePricing);

    /**
     * Обновить данные правила в системе
     *
     * @param id              Идентификатор правила, данные которого необходимо обновить
     * @param priceCodePricing Объект с обновленными данными правила
     * @return Обновленное правило
     */
    PriceCodePricing update(int id, PriceCodePricing priceCodePricing);

    /**
     * Удалить правило из системы
     *
     * @param id Идентификатор правила, которое необходимо удалить
     */
    void delete(int id);
}