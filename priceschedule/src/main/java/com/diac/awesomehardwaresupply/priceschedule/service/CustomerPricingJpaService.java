package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
import com.diac.awesomehardwaresupply.priceschedule.repository.CustomerPricingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели CustomerPricing через JPA
 */
@Service
@AllArgsConstructor
public class CustomerPricingJpaService implements CustomerPricingService {

    /**
     * Шаблон сообщения о том, что правило не найдено
     */
    private static final String CUSTOMER_PRICING_DOES_NOT_EXIST_MESSAGE = "Customer pricing #%s does not exist";

    /**
     * Репозиторий для хранения объектов CustomerPricing
     */
    private final CustomerPricingRepository customerPricingRepository;

    /**
     * Найти все правила
     *
     * @return Список правил
     */
    @Override
    public List<CustomerPricing> findAll() {
        return customerPricingRepository.findAll();
    }

    /**
     * Получить страницу с правилами
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с правилами
     */
    @Override
    public Page<CustomerPricing> getPage(PageRequest pageRequest) {
        return customerPricingRepository.findAll(pageRequest);
    }

    /**
     * Найти правило по ID
     *
     * @param id Идентификатор правила
     * @return Правило
     */
    @Override
    public CustomerPricing findById(int id) {
        return customerPricingRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(CUSTOMER_PRICING_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Добавить новое правило в систему
     *
     * @param customerPricing Новое правило
     * @return Сохраненное правило
     */
    @Override
    public CustomerPricing add(CustomerPricing customerPricing) {
        return customerPricingRepository.save(customerPricing);
    }

    /**
     * Обновить данные правила в системе
     *
     * @param id              Идентификатор правила, данные которого необходимо обновить
     * @param customerPricing Объект с обновленными данными правила
     * @return Обновленное правило
     */
    @Override
    public CustomerPricing update(int id, CustomerPricing customerPricing) {
        return customerPricingRepository.findById(id)
                .map(customerPricingInDb -> {
                    customerPricing.setId(id);
                    return customerPricingRepository.save(customerPricing);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format(CUSTOMER_PRICING_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Удалить правило из системы
     *
     * @param id Идентификатор правила, которое необходимо удалить
     */
    @Override
    public void delete(int id) {
        customerPricingRepository.findById(id)
                .ifPresentOrElse(
                        customerPricingRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(
                                    String.format(CUSTOMER_PRICING_DOES_NOT_EXIST_MESSAGE, id)
                            );
                        }
                );
    }
}