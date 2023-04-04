package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Товар"
 */
@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Product {

    /**
     * Идентификатор товара
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование товара
     */
    @NotNull(message = "Product name is required")
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    /**
     * Описание товара
     */
    @NotNull(message = "Product description is required")
    @NotBlank(message = "Product description cannot be blank")
    private String description;

    /**
     * Артикул товара
     */
    @NotNull(message = "Product sku is required")
    @NotBlank(message = "Product sku cannot be blank")
    private String sku;
}