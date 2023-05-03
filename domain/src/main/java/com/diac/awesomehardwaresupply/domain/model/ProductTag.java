package com.diac.awesomehardwaresupply.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Тэг товаров"
 */
@Entity
@Table(name = "product_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductTag {

    /**
     * Идентификатор тэга
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование тэга
     */
    @NotNull(message = "Tag name is required")
    @NotBlank(message = "Tag name cannot be blank")
    private String name;

    /**
     * Описание тэга
     */
    @NotNull(message = "Tag description is required")
    @NotBlank(message = "Tag description cannot be blank")
    private String description;

    /**
     * Фильтр, к которому принадлежит тэг
     */
    @NotNull(message = "Associated Product Filter is required")
    private ProductFilter productFilter;
}