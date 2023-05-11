package com.diac.awesomehardwaresupply.domain.dto;

/**
 * DTO ответа с ценой товара
 *
 * @param productSku      Артикул товара
 * @param calculatedPrice Рассчитанная цена товара
 */
public record ProductPriceResponseDto(String productSku, int calculatedPrice) {

}