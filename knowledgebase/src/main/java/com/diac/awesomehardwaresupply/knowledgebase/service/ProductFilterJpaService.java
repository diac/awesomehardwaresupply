package com.diac.awesomehardwaresupply.knowledgebase.service;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductFilter;
import com.diac.awesomehardwaresupply.knowledgebase.repository.ProductFilterRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели ProductFilter через JPA
 */
@Service
@AllArgsConstructor
public class ProductFilterJpaService implements ProductFilterService {

    /**
     * Шаблон сообщения о том, что фильтр не найден
     */
    private static final String PRODUCT_FILTER_DOES_NOT_EXIST_MESSAGE = "Product filter #%s does not exist";

    /**
     * Репозиторий для хранения объектов ProductFilter (Фильтр товаров)
     */
    private final ProductFilterRepository productFilterRepository;

    /**
     * Найти все фильтры товаров
     *
     * @return Список фильтров товаров
     */
    @Override
    public List<ProductFilter> findAll() {
        return productFilterRepository.findAll();
    }

    /**
     * Получить страницу с фильтрами товаров
     *
     * @param pageRequest Объект PageRequest
     * @return Страница с фильтрами товаров
     */
    @Override
    public Page<ProductFilter> getPage(PageRequest pageRequest) {
        return productFilterRepository.findAll(pageRequest);
    }

    /**
     * Найти фильтр товаров по ID
     *
     * @param id Идентификатор фильтра товаров
     * @return Фильтр товаров
     */
    @Override
    public ProductFilter findById(int id) {
        return productFilterRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PRODUCT_FILTER_DOES_NOT_EXIST_MESSAGE, id))
                );
    }

    /**
     * Добавить новый фильтр товаров в систему
     *
     * @param productFilter Новый фильтр товаров
     * @return Сохраненный фильтр товаров
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public ProductFilter add(ProductFilter productFilter) {
        try {
            return productFilterRepository.save(productFilter);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }
    }

    /**
     * Обновить данный фильтра товаров в системе
     *
     * @param id            Идентификатор фильтра товаров, данные которого необходимо обновить
     * @param productFilter Объект с обновленными данными фильтра товаров
     * @return Обновленный фильтр товаров
     * @throws ResourceNotFoundException            при попытке обновить несуществующий фильтр товаров
     * @throws ResourceConstraintViolationException в случае, если при обращении к ресурсу нарушаются наложенные на него ограничения
     */
    @Override
    public ProductFilter update(int id, ProductFilter productFilter) {
        try {
            return productFilterRepository.findById(id)
                    .map(productFilterInDb -> {
                        productFilter.setId(id);
                        return productFilterRepository.save(productFilter);
                    }).orElseThrow(
                            () -> new ResourceNotFoundException(String.format(PRODUCT_FILTER_DOES_NOT_EXIST_MESSAGE, id))
                    );
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResourceConstraintViolationException(e.getMessage());
        }

    }

    /**
     * Удалить фильтр товаров из системы
     *
     * @param id Идентификатор фильтра товаров
     * @throws ResourceNotFoundException при попытке удалить несуществующий фильтр товаров
     */
    @Override
    public void delete(int id) {
        productFilterRepository.findById(id)
                .ifPresentOrElse(
                        productFilterRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(
                                    String.format(PRODUCT_FILTER_DOES_NOT_EXIST_MESSAGE, id)
                            );
                        }
                );
    }
}