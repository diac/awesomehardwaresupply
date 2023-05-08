package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

/**
 * Модель данных "Уровень цены"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PriceLevel {

    /**
     * Идентификатор уровня цены
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя уровня цены
     */
    private String name;
}