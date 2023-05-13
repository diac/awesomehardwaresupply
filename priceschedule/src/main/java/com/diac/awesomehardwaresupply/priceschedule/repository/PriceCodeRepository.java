package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов PriceCode (Код цены)
 */
@Repository
public interface PriceCodeRepository extends JpaRepository<PriceCode, Integer> {

    /**
     * Найти код цены по имени
     *
     * @param name Имя кода цены
     * @return Optional с кодом цены. Пустой Optional, если ничего не найдено
     */
    Optional<PriceCode> findByName(String name);
}