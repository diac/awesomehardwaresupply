package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

/**
 * Модель данных "Тэг товаров"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductTag {

    /**
     * Идентификатор тэга
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование тэга
     */
    private String name;

    /**
     * Описание тэга
     */
    private String description;

    /**
     * Фильтр, к которому принадлежит тэг
     */
    private ProductFilter productFilter;
}