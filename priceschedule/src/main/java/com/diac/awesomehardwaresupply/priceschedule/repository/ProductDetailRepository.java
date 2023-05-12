package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов ProductDetail
 */
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

    /**
     * Найти подробности товара по артикулу товара
     *
     * @param productSku Артикул товара
     * @return Optional с найденными подробностями товара. Пустой Optional, если ничего не найдено
     */
    Optional<ProductDetail> findByProductSku(String productSku);
}