package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.model.Product;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели Product через JPA
 */
@Service
@AllArgsConstructor
public class ProductJpaService implements ProductService {

    /**
     * Шаблон сообщения о том, что продукт не найден
     */
    private static final String PRODUCT_DOES_NOT_EXIST_MESSAGE = "Product #%s does not exist";

    /**
     * Репозиторий для хранения объектов Product
     */
    private final ProductRepository productRepository;

    /**
     * Найти все товары
     *
     * @return Список товаров
     */
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Найти товар по ID
     *
     * @param id Идентификатор товара
     * @return Товар
     */
    @Override
    public Product findById(int id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Найти товар по артикулу
     *
     * @param sku Артикул
     * @return Товар
     */
    @Override
    public Product findBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, sku))
                );
    }

    /**
     * Добавить новый товар в систему
     *
     * @param product Новый товар
     * @return Сохраненный товар
     */
    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    /**
     * Обновить данные товара в системе
     *
     * @param id      Идентификатор товара, данные которого необходимо обновить
     * @param product Объект с обновленными данными товара
     * @return Обновленный товар
     */
    @Override
    public Product update(int id, Product product) {
        return productRepository.findById(id)
                .map(productInDb -> {
                    product.setId(id);
                    return productRepository.save(product);
                }).orElseThrow(
                        () -> new EntityNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Удалить товар из системы
     *
     * @param id Идентификатор товара, который необходимо удалить
     */
    @Override
    public void delete(int id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> {
                            throw new EntityNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, id));
                        }
                );

    }
}