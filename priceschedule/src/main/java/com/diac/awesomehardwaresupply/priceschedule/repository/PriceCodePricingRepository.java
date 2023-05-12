package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.domain.model.PriceCodePricing;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов PriceCodePricing (Правило ценообразования по коду цены)
 */
@Repository
public interface PriceCodePricingRepository extends JpaRepository<PriceCodePricing, Integer> {

    /**
     * Найти правило по уровню цены и коду цены
     *
     * @param priceLevel Уровень цены
     * @param priceCode  Код цены
     * @return Optional с найденным правилом. Пустой Optional, если ничего не найдено
     */
    Optional<PriceCodePricing> findByPriceLevelAndPriceCode(PriceLevel priceLevel, PriceCode priceCode);
}