package com.diac.awesomehardwaresupply.authentication.service;

import org.springframework.security.core.userdetails.User;

/**
 * Сервис для работы с JWT
 */
public interface JwtService {

    /**
     * Сгенерировать JWT токен
     *
     * @param principal Пользователь-принципал
     * @return Сгенерированный JWT токен
     */
    String createJwtToken(User principal);

    /**
     * Проверить валидность предоставленного токена
     *
     * @param token Токен
     */
    void validateToken(String token);
}