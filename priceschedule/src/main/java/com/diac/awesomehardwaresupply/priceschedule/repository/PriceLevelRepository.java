package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов PriceLevel (Уровень цены)
 */
@Repository
public interface PriceLevelRepository extends JpaRepository<PriceLevel, Integer> {
}