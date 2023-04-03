package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

/**
 * Модель данных "Товар"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Product {

    /**
     * Идентификатор товара
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование товара
     */
    private String name;

    /**
     * Описание товара
     */
    private String description;

    /**
     * Артикул товара
     */
    private String sku;
}