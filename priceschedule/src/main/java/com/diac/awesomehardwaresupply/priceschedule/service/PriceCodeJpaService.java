package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.priceschedule.repository.PriceCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели PriceCode через JPA
 */
@Service
@AllArgsConstructor
public class PriceCodeJpaService implements PriceCodeService {

    private static final String PRICE_CODE_DOES_NOT_EXIST_MESSAGE = "Price code #%s does not exist";

    /**
     * Репозиторий для хранения объектов PriceCode
     */
    private final PriceCodeRepository priceCodeRepository;

    /**
     * Найти все коды товаров
     *
     * @return Список кодов товаров
     */
    @Override
    public List<PriceCode> findAll() {
        return priceCodeRepository.findAll();
    }

    /**
     * Получить страницу с кодами товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с кодами товаров
     */
    @Override
    public Page<PriceCode> getPage(PageRequest pageRequest) {
        return priceCodeRepository.findAll(pageRequest);
    }

    /**
     * Найти код товаров по ID
     *
     * @param id Идентификатор кода товаров
     * @return Код товаров
     */
    @Override
    public PriceCode findById(int id) {
        return priceCodeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRICE_CODE_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Добавить новый код товаров в систему
     *
     * @param priceCode Новый код товаров
     * @return Сохраненный код товаров
     */
    @Override
    public PriceCode add(PriceCode priceCode) {
        return priceCodeRepository.save(priceCode);
    }

    /**
     * Обновить данные кода товаров в системе
     *
     * @param id        Идентификатор кода товаров, данные которого необходимо обновить
     * @param priceCode Объект с обновленными данными кода товаров
     * @return Обновленный код товаров
     */
    @Override
    public PriceCode update(int id, PriceCode priceCode) {
        return priceCodeRepository.findById(id)
                .map(priceCodeInDb -> {
                    priceCode.setId(id);
                    return priceCodeRepository.save(priceCode);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRICE_CODE_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Удалить код товаров из системы
     *
     * @param id Идентификатор кода товаров, который необходимо удалить
     */
    @Override
    public void delete(int id) {
        priceCodeRepository.findById(id)
                .ifPresentOrElse(
                        priceCodeRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(String.format(PRICE_CODE_DOES_NOT_EXIST_MESSAGE, id));
                        }
                );
    }
}