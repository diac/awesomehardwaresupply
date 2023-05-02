package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов ProductCategory (Категория товаров)
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}