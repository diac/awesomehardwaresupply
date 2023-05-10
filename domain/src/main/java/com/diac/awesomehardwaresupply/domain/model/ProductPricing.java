package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Модель данных "Правило ценообразования товара"
 */
@Entity
@Table(name = "product_pricing")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductPricing {

    /**
     * Идентификатор правила
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Артикул товара
     */
    @Column(name = "product_sku")
    @NotNull(message = "Product SKU is required")
    @NotBlank(message = "Product SKU cannot be blank")
    private String productSku;

    /**
     * Уровень ценообразования
     */
    @ManyToOne
    @JoinColumn(name = "price_level_id")
    @NotNull(message = "Price level is required")
    private PriceLevel priceLevel;

    /**
     * Шаги ценообразования
     */
    @ManyToOne
    @JoinTable(
            name = "product_pricing_pricing_step",
            joinColumns = {@JoinColumn(name = "product_pricing_id")},
            inverseJoinColumns = {@JoinColumn(name = "pricing_step_id")}
    )
    private List<PricingStep> pricingSteps;
}