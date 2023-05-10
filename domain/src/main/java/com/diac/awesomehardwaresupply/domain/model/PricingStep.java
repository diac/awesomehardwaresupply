package com.diac.awesomehardwaresupply.domain.model;

import com.diac.awesomehardwaresupply.domain.enumeration.PricingMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Шаг ценообразования"
 */
@Entity
@Table(name = "pricing_step")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PricingStep {

    /**
     * Идентификатор шага ценообразования
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Метод ценообразования
     */
    @Column(name = "pricing_method")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Pricing method is required")
    private PricingMethod pricingMethod;

    /**
     * Минимальное количество единиц товара
     */
    @Column(name = "min_quantity")
    @NotNull(message = "Minimum quantity is required")

    private Integer minQuantity;

    /**
     * Максимальное количество единиц товара
     */
    @Column(name = "max_quantity")
    @NotNull(message = "Maximum quantity is required")
    private Integer maxQuantity;

    /**
     * Величина изменения цены
     */
    @Column(name = "price_adjustment")
    @NotNull(message = "Price adjustment is required")
    private Integer priceAdjustment;
}