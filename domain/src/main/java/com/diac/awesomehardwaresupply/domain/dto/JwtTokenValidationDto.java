package com.diac.awesomehardwaresupply.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для валидации передаваемого JWT токена
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenValidationDto {

    /**
     * Значение токена
     */
    @NotNull(message = "Token is required")
    @NotBlank(message = "Token cannot be blank")
    private String token;
}