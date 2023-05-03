package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Значение спецификации товара"
 */
@Entity
@Table(name = "product_specification_value")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductSpecificationValue {

    /**
     * Идентификатор значения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Величина значения
     */
    @NotNull(message = "Value is required")
    private Float value;

    /**
     * Спецификация, которой принадлежит значение
     */
    @NotNull(message = "Associated Product Specification is required")
    private ProductSpecification productSpecification;
}