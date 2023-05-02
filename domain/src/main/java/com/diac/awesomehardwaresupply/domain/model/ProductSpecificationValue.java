package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

/**
 * Модель данных "Значение спецификации товара"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductSpecificationValue {

    /**
     * Идентификатор значения
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Величина значения
     */
    private float value;

    /**
     * Спецификация, которой принадлежит значение
     */
    private ProductSpecification productSpecification;
}