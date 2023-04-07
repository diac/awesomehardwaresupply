package com.diac.awesomehardwaresupply.authentication.service;

import com.diac.awesomehardwaresupply.authentication.model.User;

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
}