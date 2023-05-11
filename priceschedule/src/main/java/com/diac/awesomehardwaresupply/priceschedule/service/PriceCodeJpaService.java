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

    /**
     * Шаблон сообщения о том, что код цены не найден
     */
    private static final String PRICE_CODE_DOES_NOT_EXIST_MESSAGE = "Price code #%s does not exist";

    /**
     * Репозиторий для хранения объектов PriceCode
     */
    private final PriceCodeRepository priceCodeRepository;

    /**
     * Найти все коды цен
     *
     * @return Список кодов цен
     */
    @Override
    public List<PriceCode> findAll() {
        return priceCodeRepository.findAll();
    }

    /**
     * Получить страницу с кодами цен
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с кодами цен
     */
    @Override
    public Page<PriceCode> getPage(PageRequest pageRequest) {
        return priceCodeRepository.findAll(pageRequest);
    }

    /**
     * Найти код цены по ID
     *
     * @param id Идентификатор кода цен
     * @return Код цены
     */
    @Override
    public PriceCode findById(int id) {
        return priceCodeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRICE_CODE_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Добавить новый код цены в систему
     *
     * @param priceCode Новый код цены
     * @return Сохраненный код цены
     */
    @Override
    public PriceCode add(PriceCode priceCode) {
        return priceCodeRepository.save(priceCode);
    }

    /**
     * Обновить данные кода цены в системе
     *
     * @param id        Идентификатор кода цены, данные которого необходимо обновить
     * @param priceCode Объект с обновленными данными кода цены
     * @return Обновленный код цены
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
     * Удалить код цены из системы
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