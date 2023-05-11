package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.priceschedule.repository.PriceLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели PriceLevel через JPA
 */
@Service
@AllArgsConstructor
public class PriceLevelJpaService implements PriceLevelService {

    /**
     * Шаблон сообщения о том, что уровень цены не найден
     */
    private static final String PRICE_LEVEL_DOES_NOT_EXIST_MESSAGE = "Price level #%s does not exist";

    /**
     * Репозиторий для хранения объектов PriceLevel
     */
    private final PriceLevelRepository priceLevelRepository;

    /**
     * Найти все уровни цен
     *
     * @return Список уровней цен
     */
    @Override
    public List<PriceLevel> findAll() {
        return priceLevelRepository.findAll();
    }

    /**
     * Получить страницу с уровнями цен
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с уровнями цен
     */
    @Override
    public Page<PriceLevel> getPage(PageRequest pageRequest) {
        return priceLevelRepository.findAll(pageRequest);
    }

    /**
     * Найти уровень цен по ID
     *
     * @param id Идентификатор уровня цен
     * @return Уровень цен
     */
    @Override
    public PriceLevel findById(int id) {
        return priceLevelRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRICE_LEVEL_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Добавить новый уровень цен в систему
     *
     * @param priceLevel Новый уровень цен
     * @return Сохраненный уровень цен
     */
    @Override
    public PriceLevel add(PriceLevel priceLevel) {
        return priceLevelRepository.save(priceLevel);
    }

    /**
     * Обновить данные уровня цен в системе
     *
     * @param id         Идентификатор уровня цен, данные которого необходимо обновить
     * @param priceLevel Объект с обновленными данными уровня цен
     * @return Обновленный уровень цен
     */
    @Override
    public PriceLevel update(int id, PriceLevel priceLevel) {
        return priceLevelRepository.findById(id)
                .map(priceLevelInDb -> {
                    priceLevel.setId(id);
                    return priceLevelRepository.save(priceLevel);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRICE_LEVEL_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Удалить уровень цен из системы
     *
     * @param id Идентификатор уровня цен, который необходимо удалить
     */
    @Override
    public void delete(int id) {
        priceLevelRepository.findById(id)
                .ifPresentOrElse(
                        priceLevelRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(String.format(PRICE_LEVEL_DOES_NOT_EXIST_MESSAGE, id));
                        }
                );
    }
}