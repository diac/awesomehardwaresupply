package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов ProductPricing (Правило ценообразования товара)
 */
@Repository
public interface ProductPricingRepository extends JpaRepository<ProductPricing, Integer> {
}