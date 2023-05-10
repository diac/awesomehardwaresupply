package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.PriceCodePricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов PriceCodePricing (Правило ценообразования по коду цены)
 */
@Repository
public interface PriceCodePricingRepository extends JpaRepository<PriceCodePricing, Integer> {
}