package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceCodePricing;
import com.diac.awesomehardwaresupply.priceschedule.repository.PriceCodePricingRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели PriceCodePricing через JPA
 */
@Service
@AllArgsConstructor
public class PriceCodePricingJpaService implements PriceCodePricingService {

    /**
     * Шаблон сообщения о том, что правило не найдено
     */
    private static final String PRICE_CODE_PRICING_DOES_NOT_EXIST_MESSAGE = "Price code pricing #%s does not exist";

    /**
     * Репозиторий для хранения объектов PriceCodePricing
     */
    private final PriceCodePricingRepository priceCodePricingRepository;

    /**
     * Найти все правила
     *
     * @return Список правил
     */
    @Override
    public List<PriceCodePricing> findAll() {
        return priceCodePricingRepository.findAll();
    }

    /**
     * Получить страницу с правилами
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с правилами
     */
    @Override
    public Page<PriceCodePricing> getPage(PageRequest pageRequest) {
        return priceCodePricingRepository.findAll(pageRequest);
    }

    /**
     * Найти правило по ID
     *
     * @param id Идентификатор правила
     * @return Правило
     * @throws ResourceNotFoundException Если ничего не найдено
     */
    @Override
    public PriceCodePricing findById(int id) {
        return priceCodePricingRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format(PRICE_CODE_PRICING_DOES_NOT_EXIST_MESSAGE, id)
                        )
                );
    }

    /**
     * Добавить новое правило в систему
     *
     * @param priceCodePricing Новое правило
     * @return Сохраненное правило
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public PriceCodePricing add(PriceCodePricing priceCodePricing) {
        try {
            return priceCodePricingRepository.save(priceCodePricing);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }
    }

    /**
     * Обновить данные правила в системе
     *
     * @param id               Идентификатор правила, данные которого необходимо обновить
     * @param priceCodePricing Объект с обновленными данными правила
     * @return Обновленное правило
     * @throws ResourceNotFoundException            При попытке обновить несуществующее правило
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public PriceCodePricing update(int id, PriceCodePricing priceCodePricing) {
        try {
            return priceCodePricingRepository.findById(id)
                    .map(priceCodePricingInDb -> {
                        priceCodePricing.setId(id);
                        return priceCodePricingRepository.save(priceCodePricing);
                    }).orElseThrow(
                            () -> new ResourceNotFoundException(
                                    String.format(PRICE_CODE_PRICING_DOES_NOT_EXIST_MESSAGE, id)
                            )
                    );
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }
    }

    /**
     * Удалить правило из системы
     *
     * @param id Идентификатор правила, которое необходимо удалить
     * @throws ResourceNotFoundException При попытке удалить несуществующее правило
     */
    @Override
    public void delete(int id) {
        priceCodePricingRepository.findById(id)
                .ifPresentOrElse(
                        priceCodePricingRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(
                                    String.format(PRICE_CODE_PRICING_DOES_NOT_EXIST_MESSAGE, id)
                            );
                        }
                );
    }
}