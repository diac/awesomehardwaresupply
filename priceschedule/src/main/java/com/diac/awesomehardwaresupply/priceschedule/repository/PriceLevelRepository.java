package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов PriceLevel (Уровень цены)
 */
@Repository
public interface PriceLevelRepository extends JpaRepository<PriceLevel, Integer> {

    /**
     * Найти уровень цены по имени
     *
     * @param name Имя уровня цены
     * @return Optional с уровнем цены. Пустой Optional, если ничего не найдено
     */
    Optional<PriceLevel> findByName(String name);
}