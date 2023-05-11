package com.diac.awesomehardwaresupply.priceschedule.service;

import com.diac.awesomehardwaresupply.domain.dto.ProductPriceRequestDto;
import com.diac.awesomehardwaresupply.domain.dto.ProductPriceResponseDto;

/**
 * Сервис, реализующий логику расчета цен товаров
 */
public interface ProductPriceService {

    /**
     * Рассчитать цену товара
     *
     * @param productPriceRequestDto Объект-запрос цены
     * @return Объект ProductPriceResponseDto с рассчитанной ценой
     */
    ProductPriceResponseDto calculate(ProductPriceRequestDto productPriceRequestDto);
}