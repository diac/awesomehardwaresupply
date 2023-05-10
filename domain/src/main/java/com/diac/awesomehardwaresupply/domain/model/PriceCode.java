package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Код цены"
 */
@Entity
@Table(name = "price_code")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PriceCode {

    /**
     * Идентификатор кода цены
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя кода цены
     */
    @NotNull(message = "Price code name is required")
    @NotBlank(message = "Price code name cannot be blank")
    private String name;
}