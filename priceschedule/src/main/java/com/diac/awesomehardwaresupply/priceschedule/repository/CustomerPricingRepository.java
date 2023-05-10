package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов CustomerPricing (Правило персонального ценообразования товара)
 */
@Repository
public interface CustomerPricingRepository extends JpaRepository<CustomerPricing, Integer> {
}