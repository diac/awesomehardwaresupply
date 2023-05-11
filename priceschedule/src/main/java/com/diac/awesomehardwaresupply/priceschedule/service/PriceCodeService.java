package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели PriceCode
 */
public interface PriceCodeService {

    /**
     * Найти все коды цен
     *
     * @return Список кодов цен
     */
    List<PriceCode> findAll();

    /**
     * Получить страницу с кодами цен
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с кодами цен
     */
    Page<PriceCode> getPage(PageRequest pageRequest);

    /**
     * Найти код цены по ID
     *
     * @param id Идентификатор кода цен
     * @return Код цены
     */
    PriceCode findById(int id);

    /**
     * Добавить новый код цены в систему
     *
     * @param priceCode Новый код цены
     * @return Сохраненный код цены
     */
    PriceCode add(PriceCode priceCode);

    /**
     * Обновить данные кода цены в системе
     *
     * @param id        Идентификатор кода цены, данные которого необходимо обновить
     * @param priceCode Объект с обновленными данными кода цены
     * @return Обновленный код цены
     */
    PriceCode update(int id, PriceCode priceCode);

    /**
     * Удалить код цены из системы
     *
     * @param id Идентификатор кода товаров, который необходимо удалить
     */
    void delete(int id);
}