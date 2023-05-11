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
     * Найти все коды товаров
     *
     * @return Список кодов товаров
     */
    List<PriceCode> findAll();

    /**
     * Получить страницу с кодами товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с кодами товаров
     */
    Page<PriceCode> getPage(PageRequest pageRequest);

    /**
     * Найти код товаров по ID
     *
     * @param id Идентификатор кода товаров
     * @return Код товаров
     */
    PriceCode findById(int id);

    /**
     * Добавить новый код товаров в систему
     *
     * @param priceCode Новый код товаров
     * @return Сохраненный код товаров
     */
    PriceCode add(PriceCode priceCode);

    /**
     * Обновить данные кода товаров в системе
     *
     * @param id        Идентификатор кода товаров, данные которого необходимо обновить
     * @param priceCode Объект с обновленными данными кода товаров
     * @return Обновленный код товаров
     */
    PriceCode update(int id, PriceCode priceCode);

    /**
     * Удалить код товаров из системы
     *
     * @param id Идентификатор кода товаров, который необходимо удалить
     */
    void delete(int id);
}