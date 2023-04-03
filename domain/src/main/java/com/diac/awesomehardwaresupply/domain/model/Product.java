package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;

    /**
     * Описание товара
     */
    private String description;

    /**
     * Артикул товара
     */
    private String sku;
}