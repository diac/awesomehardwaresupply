package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

/**
 * Модель данных "Диапазон значений спецификации товара"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductSpecificationValueRange {

    /**
     * Идентификатор диапазона значений
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Минимальная величина значения
     */
    private float valueMin;

    /**
     * Максимальная величина значения
     */
    private float valueMax;

    /**
     * Спецификация, которой принадлежит значение
     */
    private ProductSpecification productSpecification;
}