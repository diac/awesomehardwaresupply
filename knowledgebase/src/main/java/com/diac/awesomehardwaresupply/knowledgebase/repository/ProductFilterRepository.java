package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов ProductFilter (Фильтр товаров)
 */
@Repository
public interface ProductFilterRepository extends JpaRepository<ProductFilter, Integer> {
}