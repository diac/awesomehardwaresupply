package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
import com.diac.awesomehardwaresupply.priceschedule.repository.ProductPricingRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductPricing через JPA
 */
@Service
@AllArgsConstructor
public class ProductPricingJpaService implements ProductPricingService {

    /**
     * Шаблон сообщения о том, что правило не найдено
     */
    private static final String PRODUCT_PRICING_DOES_NOT_EXIST_MESSAGE = "Product pricing #%s does not exist";

    /**
     * Репозиторий для хранения объектов ProductPricing
     */
    private final ProductPricingRepository productPricingRepository;

    /**
     * Найти все правила
     *
     * @return Список правил
     */
    @Override
    public List<ProductPricing> findAll() {
        return productPricingRepository.findAll();
    }

    /**
     * Получить страницу с правилами
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с правилами
     */
    @Override
    public Page<ProductPricing> getPage(PageRequest pageRequest) {
        return productPricingRepository.findAll(pageRequest);
    }

    /**
     * Найти правило по ID
     *
     * @param id Идентификатор правила
     * @return Правило
     * @throws ResourceNotFoundException если ничего не найдено
     */
    @Override
    public ProductPricing findById(int id) {
        return productPricingRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_PRICING_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Добавить новое правило в систему
     *
     * @param productPricing Новое правило
     * @return Сохраненное правило
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public ProductPricing add(ProductPricing productPricing) {
        try {
            return productPricingRepository.save(productPricing);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }
    }

    /**
     * Обновить данные правила в системе
     *
     * @param id             Идентификатор правила, данные которого необходимо обновить
     * @param productPricing Объект с обновленными данными правила
     * @return Обновленное правило
     * @throws ResourceNotFoundException            При попытке обновить несуществующее правило
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public ProductPricing update(int id, ProductPricing productPricing) {
        try {
            return productPricingRepository.findById(id)
                    .map(productPricingInDb -> {
                        productPricing.setId(id);
                        return productPricingRepository.save(productPricing);
                    }).orElseThrow(
                            () -> new ResourceNotFoundException(String.format(PRODUCT_PRICING_DOES_NOT_EXIST_MESSAGE, id))
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
        productPricingRepository.findById(id)
                .ifPresentOrElse(
                        productPricingRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(
                                    String.format(PRODUCT_PRICING_DOES_NOT_EXIST_MESSAGE, id)
                            );
                        }
                );
    }
}