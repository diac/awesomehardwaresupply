package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов CustomerPricing (Правило персонального ценообразования товара)
 */
@Repository
public interface CustomerPricingRepository extends JpaRepository<CustomerPricing, Integer> {

    /**
     * Найти правило по персональному номеру клиента и артикулу товара
     *
     * @param customerNumber Персональный номер клиента
     * @param productSku     Артикул товара
     * @return Optional с найденным правилом. Пустой Optional, если ничего не найдено
     */
    Optional<CustomerPricing> findByCustomerNumberAndProductSku(String customerNumber, String productSku);
}