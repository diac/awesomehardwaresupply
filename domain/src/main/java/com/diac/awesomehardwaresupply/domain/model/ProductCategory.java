package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

import java.util.Set;

/**
 * Модель данных "Категория товаров"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductCategory {

    /**
     * Идентификатор категории товаров
     */
    private int id;

    /**
     * Наименование категории товаров
     */
    private String name;

    /**
     * Описание категории товаров
     */
    private String description;

    /**
     * Родительская категория товаров
     */
    private ProductCategory parent;

    /**
     * Дочерние категории товаров
     */
    private Set<ProductCategory> children;
}