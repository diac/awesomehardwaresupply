package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов Product (Товар)
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}