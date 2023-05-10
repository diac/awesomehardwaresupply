package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Уровень цены"
 */
@Entity
@Table(name = "price_level")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PriceLevel {

    /**
     * Идентификатор уровня цены
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя уровня цены
     */
    @NotNull(message = "Price level name is required")
    @NotBlank(message = "Price level name cannot be blank")
    private String name;
}