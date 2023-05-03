package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Фильтр товаров"
 */
@Entity
@Table(name = "product_filter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductFilter {

    /**
     * Идентификатор фильтра товаров
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование фильтра товаров
     */
    @NotNull(message = "Product filter name is required")
    @NotBlank(message = "Product filter name cannot be blank")
    private String name;

    /**
     * Описание фильтра товаров
     */
    @NotNull(message = "Product filter description is required")
    @NotBlank(message = "Product filter description cannot be blank")
    private String description;
}