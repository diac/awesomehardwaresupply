package com.diac.awesomehardwaresupply.priceschedule.respository;

import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов PriceCode (Код цены)
 */
@Repository
public interface PriceCodeRepository extends JpaRepository<PriceCode, Integer> {
}