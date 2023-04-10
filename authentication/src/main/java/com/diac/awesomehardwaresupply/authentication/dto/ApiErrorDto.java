package com.diac.awesomehardwaresupply.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO для передачи данных об ошибках API
 */
@Data
@AllArgsConstructor
@Builder
public class ApiErrorDto {

    /**
     * Сообщение об ошибке
     */
    private String message;
}