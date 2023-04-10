package com.diac.awesomehardwaresupply.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для передачи сгенерированных JWT токенов
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenResponseDto {

    /**
     * Токен
     */
    private String token;
}