package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Модель данных "Правило ценообразования по коду цены"
 */
@Entity
@Table(name = "price_code_pricing")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PriceCodePricing implements Pricing {

    /**
     * Идентификатор правила
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Уровень цены
     */
    @ManyToOne
    @JoinColumn(name = "price_level_id")
    @NotNull(message = "Price level is required")
    private PriceLevel priceLevel;

    /**
     * Код цены
     */
    @ManyToOne
    @JoinColumn(name = "price_code_id")
    @NotNull(message = "Price code is required")
    private PriceCode priceCode;

    /**
     * Шаги ценообразования
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "price_code_pricing_pricing_step",
            joinColumns = {@JoinColumn(name = "price_code_pricing_id")},
            inverseJoinColumns = {@JoinColumn(name = "pricing_step_id")}
    )
    List<PricingStep> pricingSteps;
}