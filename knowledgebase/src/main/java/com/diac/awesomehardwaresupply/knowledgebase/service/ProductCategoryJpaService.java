package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductCategory;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductCategory через JPA
 */
@Service
@AllArgsConstructor
public class ProductCategoryJpaService implements ProductCategoryService {

    /**
     * Шаблон сообщения о том, что категория товаров не найдена
     */
    private static final String PRODUCT_CATEGORY_DOES_NOT_EXIST_MESSAGE = "Product category #%s does not exist";

    /**
     * Репозиторий для хранения объектов ProductCategory (Категория товаров)
     */
    private final ProductCategoryRepository productCategoryRepository;

    /**
     * Найти все категории товаров
     *
     * @return Список категорий товаров
     */
    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    /**
     * Получить страницу с категориями товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с категориями товаров
     */
    @Override
    public Page<ProductCategory> getPage(PageRequest pageRequest) {
        return productCategoryRepository.findAll(pageRequest);
    }

    /**
     * Найти категорию товаров по ID
     *
     * @param id Идентификатор категории товаров
     * @return Категория товаров
     */
    @Override
    public ProductCategory findById(int id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_CATEGORY_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Добавить новую категорию товаров в систему
     *
     * @param productCategory Новая категория товаров
     * @return Сохраненная категория товаров
     */
    @Override
    public ProductCategory add(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    /**
     * Обновить данные категории товаров в системе
     *
     * @param id              Идентификатор категории товаров, данные которой необходимо обновить
     * @param productCategory Объект с обновленными данными категории товаров
     * @return Обновленная категория товаров
     */
    @Override
    public ProductCategory update(int id, ProductCategory productCategory) {
        return productCategoryRepository.findById(id)
                .map(productCategoryInDb -> {
                    productCategory.setId(id);
                    return productCategoryRepository.save(productCategory);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_CATEGORY_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Удалить категорию товаров из системы
     *
     * @param id Идентификатор категории товаров, которую необходимо удалить
     */
    @Override
    public void delete(int id) {
        productCategoryRepository.findById(id)
                .ifPresentOrElse(
                        productCategoryRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(
                                    String.format(PRODUCT_CATEGORY_DOES_NOT_EXIST_MESSAGE, id)
                            );
                        }
                );
    }
}