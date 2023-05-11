package com.diac.awesomehardwaresupply.knowledgebase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO запроса цены товара
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceRequestDto {

    /**
     * Артикул товара
     */
    @NotNull(message = "Product SKU is required")
    @NotBlank(message = "Product SKU cannot be blank")
    private String productSku;

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