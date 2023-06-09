package com.diac.awesomehardwaresupply.knowledgebase.repository;

import com.diac.awesomehardwaresupply.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов Product (Товар)
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Найти товар по артикулу
     *
     * @param sku Артикул
     * @return Optional с товаром. Пустой Optional, если ничего не найдено
     */
    Optional<Product> findBySku(String sku);
}