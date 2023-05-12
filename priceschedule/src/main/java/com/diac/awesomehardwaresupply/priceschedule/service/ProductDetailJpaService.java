package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import com.diac.awesomehardwaresupply.priceschedule.repository.ProductDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductDetail через JPA
 */
@Service
@AllArgsConstructor
public class ProductDetailJpaService implements ProductDetailService {

    /**
     * Шаблон сообщения о том, что подробности товара не найдены
     */
    private static final String PRODUCT_DETAIL_NOT_FOUND_MESSAGE = "Product detail #%s does not exist";

    /**
     * Репозиторий для хранения объектов ProductDetail
     */
    private final ProductDetailRepository productDetailRepository;

    /**
     * Найти все подробности товаров
     *
     * @return Список с подробностями товаров
     */
    @Override
    public List<ProductDetail> findAll() {
        return productDetailRepository.findAll();
    }

    /**
     * Получить страницу с подробностями товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с подробностями товаров
     */
    @Override
    public Page<ProductDetail> getPage(PageRequest pageRequest) {
        return productDetailRepository.findAll(pageRequest);
    }

    /**
     * Найти подробности товара по ID
     *
     * @param id Идентификатор подробностей товара
     * @return Подробности товара
     */
    @Override
    public ProductDetail findById(int id) {
        return productDetailRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_DETAIL_NOT_FOUND_MESSAGE, id))
                );
    }

    /**
     * Найти подробности товара по артикулу товара
     *
     * @param productSku Артикул товара
     * @return Подробности товара
     */
    @Override
    public ProductDetail findByProductSku(String productSku) {
        return productDetailRepository.findByProductSku(productSku)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_DETAIL_NOT_FOUND_MESSAGE, productSku))
                );
    }

    /**
     * Добавить новые подробности товара в систему
     *
     * @param productDetail Подробности товара
     * @return Сохраненные подробности товара
     */
    @Override
    public ProductDetail add(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    /**
     * Обновить подробности товара в системе
     *
     * @param id            Идентификатор подробностей товара
     * @param productDetail Объект с обновленными данными подробностей товара
     * @return Обновленные подробности товара
     */
    @Override
    public ProductDetail update(int id, ProductDetail productDetail) {
        return productDetailRepository.findById(id)
                .map(productDetailInDb -> {
                    productDetail.setId(id);
                    return productDetailRepository.save(productDetail);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_DETAIL_NOT_FOUND_MESSAGE, id))
                );
    }

    /**
     * Удалить подробности товара из системы
     *
     * @param id Идентификатор подробностей, которые необходимо удалить
     */
    @Override
    public void delete(int id) {
        productDetailRepository.findById(id)
                .ifPresentOrElse(
                        productDetailRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(
                                    String.format(PRODUCT_DETAIL_NOT_FOUND_MESSAGE, id)
                            );
                        }
                );
    }
}