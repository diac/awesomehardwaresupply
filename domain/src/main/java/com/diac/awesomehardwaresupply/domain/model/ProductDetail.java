package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Подробности товара"
 */
@Entity
@Table(name = "product_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductDetail {

    /**
     * Идентификатор подробностей товара
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Артикул товара
     */
    @NotNull(message = "Product SKU is required")
    @NotBlank(message = "Product SKU cannot be blank")
    @Column(name = "product_sku")
    private String productSku;

    /**
     * Цена товара по прейскуранту
     */
    @NotNull(message = "Product list price is required")
    @Column(name = "list_price")
    private Integer listPrice;

    /**
     * Стоимость товара
     */
    @NotNull(message = "Product cost is required")
    private Integer cost;

    /**
     * Код товара
     */
    @Column(name = "price_code")
    @NotNull(message = "Product price code is required")
    @NotBlank(message = "Product price code cannot be blank")
    private String priceCode;
}