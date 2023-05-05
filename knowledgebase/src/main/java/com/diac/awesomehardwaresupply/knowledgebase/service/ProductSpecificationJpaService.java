package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductSpecification;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductSpecificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductSpecification через JPA
 */
@Service
@AllArgsConstructor
public class ProductSpecificationJpaService implements ProductSpecificationService {

    /**
     * Шаблон сообщения о том, что спецификация товаров не найдена
     */
    private static final String PRODUCT_SPECIFICATION_DOES_NOT_EXIST_MESSAGE = "Product specification #%s does not exist";

    /**
     * Репозиторий для хранения объектов ProductSpecification (Спецификация товаров)
     */
    private final ProductSpecificationRepository productSpecificationRepository;

    /**
     * Найти все спецификации товаров
     *
     * @return Список спецификаций товаров
     */
    @Override
    public List<ProductSpecification> findAll() {
        return productSpecificationRepository.findAll();
    }

    /**
     * Получить страницу со спецификациями товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница со спецификациями товаров
     */
    @Override
    public Page<ProductSpecification> getPage(PageRequest pageRequest) {
        return productSpecificationRepository.findAll(pageRequest);
    }

    /**
     * Найти спецификацию товаров по ID
     *
     * @param id Идентификатор спецификации товаров
     * @return Спецификация товаров
     */
    @Override
    public ProductSpecification findById(int id) {
        return productSpecificationRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format(PRODUCT_SPECIFICATION_DOES_NOT_EXIST_MESSAGE, id)
                        )
                );
    }

    /**
     * Добавить новую спецификацию товаров в систему
     *
     * @param productSpecification Новая спецификация товаров
     * @return Сохраненная спецификация товаров
     */
    @Override
    public ProductSpecification add(ProductSpecification productSpecification) {
        return productSpecificationRepository.save(productSpecification);
    }

    /**
     * Обновить данные спецификации товаров в системе
     *
     * @param id                   Идентификатор спецификации товаров, данные которой необходимо обновить
     * @param productSpecification Объект с обновленными данными спецификации товаров
     * @return Обновленная спецификация товаров
     */
    @Override
    public ProductSpecification update(int id, ProductSpecification productSpecification) {
        return productSpecificationRepository.findById(id)
                .map(productSpecificationInDb -> {
                    productSpecification.setId(id);
                    return productSpecificationRepository.save(productSpecification);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format(PRODUCT_SPECIFICATION_DOES_NOT_EXIST_MESSAGE, id)
                        )
                );
    }

    /**
     * Удалить спецификацию товаров из системы
     *
     * @param id Идентификатор спецификации товаров, которую необходимо удалить
     */
    @Override
    public void delete(int id) {
        productSpecificationRepository.findById(id)
                .ifPresentOrElse(
                        productSpecificationRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(
                                    String.format(PRODUCT_SPECIFICATION_DOES_NOT_EXIST_MESSAGE, id)
                            );
                        }
                );
    }
}