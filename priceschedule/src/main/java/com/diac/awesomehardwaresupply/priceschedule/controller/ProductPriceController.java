package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.dto.ProductPriceRequestDto;
import com.diac.awesomehardwaresupply.domain.dto.ProductPriceResponseDto;
import com.diac.awesomehardwaresupply.priceschedule.service.ProductPriceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, реализующий доступ к логике расчета цен товаров
 */
@RestController
@AllArgsConstructor
@RequestMapping("/product_price")
public class ProductPriceController {

    /**
     * Сервис, реализующий логику расчета цен товаров
     */
    private final ProductPriceService productPriceService;

    /**
     * Рассчитать цену товара
     * @param productPriceRequestDto Объект-запрос цены
     * @return Ответ -- объект ProductPriceResponseDto с рассчитанной ценой
     */
    @PostMapping("/calculate")
    public ResponseEntity<ProductPriceResponseDto> calculate(
            @RequestBody @Valid ProductPriceRequestDto productPriceRequestDto
    ) {
        return new ResponseEntity<>(
                productPriceService.calculate(productPriceRequestDto),
                HttpStatus.OK
        );
    }
}