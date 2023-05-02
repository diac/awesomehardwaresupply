package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

/**
 * Модель данных "Фильтр товаров"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductFilter {

    /**
     * Идентификатор фильтра товаров
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование фильтра товаров
     */
    private String name;

    /**
     * Описание фильтра товаров
     */
    private String description;
}