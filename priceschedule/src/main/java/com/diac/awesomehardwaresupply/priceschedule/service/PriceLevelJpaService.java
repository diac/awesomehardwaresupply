package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.priceschedule.repository.PriceLevelRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
     * Найти уровень цен по имени
     *
     * @param name Имя уровня цен
     * @return Уровень цен
     * @throws ResourceNotFoundException если ничего не найдено
     */
    @Override
    public PriceLevel findByName(String name) {
        return priceLevelRepository.findByName(name)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRICE_LEVEL_DOES_NOT_EXIST_MESSAGE, name))
                );
    }

    /**
     * Добавить новый уровень цен в систему
     *
     * @param priceLevel Новый уровень цен
     * @return Сохраненный уровень цен
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public PriceLevel add(PriceLevel priceLevel) {
        try {
            return priceLevelRepository.save(priceLevel);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }
    }

    /**
     * Обновить данные уровня цен в системе
     *
     * @param id         Идентификатор уровня цен, данные которого необходимо обновить
     * @param priceLevel Объект с обновленными данными уровня цен
     * @return Обновленный уровень цен
     * @throws ResourceNotFoundException            при попытке обновить несуществующий уровень цен
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public PriceLevel update(int id, PriceLevel priceLevel) {
        try {
            return priceLevelRepository.findById(id)
                    .map(priceLevelInDb -> {
                        priceLevel.setId(id);
                        return priceLevelRepository.save(priceLevel);
                    }).orElseThrow(
                            () -> new ResourceNotFoundException(String.format(PRICE_LEVEL_DOES_NOT_EXIST_MESSAGE, id))
                    );
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }
    }

    /**
     * Удалить уровень цен из системы
     *
     * @param id Идентификатор уровня цен, который необходимо удалить
     * @throws ResourceNotFoundException при попытке удалить несуществующий уровень цен
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