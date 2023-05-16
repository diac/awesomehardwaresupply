package com.diac.awesomehardwaresupply.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO запроса цены товара
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPriceRequestDto {

    /**
     * Артикул товара
     */
    @NotNull(message = "Product SKU is required")
    @NotBlank(message = "Product SKU cannot be blank")
    private String productSku;

    /**
     * Количество единиц товара
     */
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than zero")
    private Integer quantity;

    /**
     * Номер клиента
     */
    private String customerNumber;

    /**
     * Уровень цены
     */
    private String priceLevel;

    /**
     * Код цены
     */
    private String priceCode;
}