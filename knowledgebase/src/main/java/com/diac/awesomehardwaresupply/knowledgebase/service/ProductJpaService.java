package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.Product;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public Product add(Product product) {
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }
    }

    /**
     * Обновить данные товара в системе
     *
     * @param id      Идентификатор товара, данные которого необходимо обновить
     * @param product Объект с обновленными данными товара
     * @return Обновленный товар
     * @throws ResourceNotFoundException При попытке обновить несуществующий товар
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public Product update(int id, Product product) {
        try {
            return productRepository.findById(id)
                    .map(productInDb -> {
                        product.setId(id);
                        return productRepository.save(product);
                    }).orElseThrow(
                            () -> new ResourceNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST_MESSAGE, id))
                    );
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }
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