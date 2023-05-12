package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Модель данных "Товар"
 */
@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Product {

    /**
     * Идентификатор товара
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование товара
     */
    @NotNull(message = "Product name is required")
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    /**
     * Описание товара
     */
    @NotNull(message = "Product description is required")
    @NotBlank(message = "Product description cannot be blank")
    private String description;

    /**
     * Артикул товара
     */
    @NotNull(message = "Product sku is required")
    @NotBlank(message = "Product sku cannot be blank")
    private String sku;

    /**
     * Категория товара
     */
    @ManyToOne
    @JoinColumn(name = "product_category_id")
    @NotNull(message = "Product Category is required")
    private ProductCategory productCategory;

    /**
     * Тэги товара
     */
    @ManyToMany
    @JoinTable(
            name = "product_product_tag",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_tag_id")}
    )
    private List<ProductTag> productTags;

    /**
     * Значения спецификаций товара
     */
    @ManyToMany
    @JoinTable(
            name = "product_product_specification_value",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_specification_value_id")}
    )
    private List<ProductSpecificationValue> productSpecificationValues;

    /**
     * Значения диапазонов спецификаций товара
     */
    @ManyToMany
    @JoinTable(
            name = "product_product_specification_value_range",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_specification_value_range_id")}
    )
    private List<ProductSpecificationValueRange> productSpecificationValueRanges;
}