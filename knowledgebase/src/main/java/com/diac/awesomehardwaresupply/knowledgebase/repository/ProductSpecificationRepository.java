package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов ProductSpecification (Спецификация товаров)
 */
@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Integer> {
}