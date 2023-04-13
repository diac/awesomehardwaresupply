package com.diac.awesomehardwaresupply.gateway.service;

import com.diac.awesomehardwaresupply.domain.enumeration.Authority;

import java.util.List;

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

    List<Authority> getAuthoritiesFromToken(String token);
}