package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

/**
 * Модель данных "Спецификация товаров"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductSpecification {

    /**
     * Идентификатор спецификации товаров
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование спецификации
     */
    private String name;

    /**
     * Описание спецификации
     */
    private String description;

    /**
     * Единица измерения
     */
    private String units;
}