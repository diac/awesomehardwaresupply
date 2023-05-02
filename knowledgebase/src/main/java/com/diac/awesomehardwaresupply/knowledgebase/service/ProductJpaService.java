package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.Product;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
     * Получить страницу с товарами
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с товарами
     */
    @Override
    public Page<Product> getPage(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    /**
     * Найти товар по ID
     *
     * @param id Идентификатор товара
     * @return Товар
     * @throws ResourceNotFoundException Если ничего не найдено
     */
    @Override
    public Product findById(int id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Найти товар по артикулу
     *
     * @param sku Артикул
     * @return Товар
     * @throws ResourceNotFoundException Если ничего не найдено
     */
    @Override
    public Product findBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, sku))
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
     * @throws ResourceNotFoundException При попытке обновить несуществующий товар
     */
    @Override
    public Product update(int id, Product product) {
        return productRepository.findById(id)
                .map(productInDb -> {
                    product.setId(id);
                    return productRepository.save(product);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Удалить товар из системы
     *
     * @param id Идентификатор товара, который необходимо удалить
     * @throws ResourceNotFoundException При попытке удалить несуществующий товар
     */
    @Override
    public void delete(int id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, id));
                        }
                );

    }
}