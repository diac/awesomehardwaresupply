package com.diac.awesomehardwaresupply.domain.model;

import lombok.*;

/**
 * Модель данных "Код цены"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PriceCode {

    /**
     * Идентификатор кода цены
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя кода цены
     */
    private String name;
}