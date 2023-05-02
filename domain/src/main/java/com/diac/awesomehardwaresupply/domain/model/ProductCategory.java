package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

/**
 * Модель данных "Категория товаров"
 */
@Entity
@Table(name = "product_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductCategory {

    /**
     * Идентификатор категории товаров
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование категории товаров
     */
    @NotNull(message = "Product category name is required")
    @NotBlank(message = "Product category name cannot be blank")
    private String name;

    /**
     * Описание категории товаров
     */
    @NotNull(message = "Product category description is required")
    @NotBlank(message = "Product category description cannot be blank")
    private String description;

    /**
     * Родительская категория товаров
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private ProductCategory parent;

    /**
     * Дочерние категории товаров
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<ProductCategory> children;
}