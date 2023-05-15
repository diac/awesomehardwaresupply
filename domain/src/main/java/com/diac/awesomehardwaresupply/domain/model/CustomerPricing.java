package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Модель данных "Правило персонального ценообразования товара"
 */
@Entity
@Table(name = "customer_pricing")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class CustomerPricing implements Pricing {

    /**
     * Идентификатор правила
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Персональный номер клиента
     */
    @Column(name = "customer_number")
    @NotNull(message = "Customer number is required")
    @NotBlank(message = "Customer number cannot be blank")
    private String customerNumber;

    /**
     * Артикул товара
     */
    @Column(name = "product_sku")
    @NotNull(message = "Product SKU is required")
    @NotBlank(message = "Product SKU cannot be blank")
    private String productSku;

    /**
     * Шаги ценообразования
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_pricing_pricing_step",
            joinColumns = {@JoinColumn(name = "customer_pricing_id")},
            inverseJoinColumns = {@JoinColumn(name = "pricing_step_id")}
    )
    private List<PricingStep> pricingSteps;
}