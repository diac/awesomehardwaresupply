package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Диапазон значений спецификации товара"
 */
@Entity
@Table(name = "product_specification_value_range")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductSpecificationValueRange {

    /**
     * Идентификатор диапазона значений
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Минимальная величина значения
     */
    @Column(name = "value_min")
    @NotNull(message = "Value minimum is required")
    private Float valueMin;

    /**
     * Максимальная величина значения
     */
    @Column(name = "value_max")
    @NotNull(message = "Value maximum is required")
    private Float valueMax;

    /**
     * Спецификация, которой принадлежит значение
     */
    @ManyToOne
    @JoinColumn(name = "product_specification_id")
    @NotNull(message = "Associated Product Specification is required")
    private ProductSpecification productSpecification;
}