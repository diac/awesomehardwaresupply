package com.diac.awesomehardwaresupply.gateway.service;

/**
 * Сервис для работы с JWT
 */
public interface JwtService {

    /**
     * Проверить валидность предоставленного токена
     *
     * @param token Токен
     */
    void validateToken(String token);
}