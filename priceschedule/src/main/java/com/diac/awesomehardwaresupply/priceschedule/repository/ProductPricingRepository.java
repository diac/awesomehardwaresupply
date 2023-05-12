package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов ProductPricing (Правило ценообразования товара)
 */
@Repository
public interface ProductPricingRepository extends JpaRepository<ProductPricing, Integer> {

    /**
     * Найти правило по артикулу товара и уровню цены
     *
     * @param productSku Артикул товара
     * @param priceLevel Уровень цены
     * @return Optional с найденным правилом. Пустой Optional, если ничего не найдено
     */
    Optional<ProductPricing> findByProductSkuAndPriceLevel(String productSku, PriceLevel priceLevel);
}