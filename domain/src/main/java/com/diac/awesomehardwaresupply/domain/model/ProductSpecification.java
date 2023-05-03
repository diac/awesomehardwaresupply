package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Спецификация товаров"
 */
@Entity
@Table(name = "product_specification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductSpecification {

    /**
     * Идентификатор спецификации товаров
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование спецификации
     */
    @NotNull(message = "Specification name is required")
    @NotBlank(message = "Specification name cannot be blank")
    private String name;

    /**
     * Описание спецификации
     */
    @NotNull(message = "Specification description is required")
    @NotBlank(message = "Specification description cannot be blank")
    private String description;

    /**
     * Единица измерения
     */
    @NotNull(message = "Specification units is required")
    @NotBlank(message = "Specification units cannot be blank")
    private String units;
}