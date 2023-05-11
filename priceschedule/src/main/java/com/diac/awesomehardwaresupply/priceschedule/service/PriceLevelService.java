package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Сервис для работы с объектами модели PriceLevel
 */
public interface PriceLevelService {

    /**
     * Найти все уровни цен
     *
     * @return Список уровней цен
     */
    List<PriceLevel> findAll();

    /**
     * Получить страницу с уровнями цен
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с уровнями цен
     */
    Page<PriceLevel> getPage(PageRequest pageRequest);

    /**
     * Найти уровень цен по ID
     *
     * @param id Идентификатор уровня цен
     * @return Уровень цен
     */
    PriceLevel findById(int id);

    /**
     * Добавить новый уровень цен в систему
     *
     * @param priceLevel Новый уровень цен
     * @return Сохраненный уровень цен
     */
    PriceLevel add(PriceLevel priceLevel);

    /**
     * Обновить данные уровня цен в системе
     *
     * @param id         Идентификатор уровня цен, данные которого необходимо обновить
     * @param priceLevel Объект с обновленными данными уровня цен
     * @return Обновленный уровень цен
     */
    PriceLevel update(int id, PriceLevel priceLevel);

    /**
     * Удалить уровень цен из системы
     *
     * @param id Идентификатор уровня цен, который необходимо удалить
     */
    void delete(int id);
}