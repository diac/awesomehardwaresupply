package com.diac.awesomehardwaresupply.authentication.service;

import com.diac.awesomehardwaresupply.authentication.dto.JwtTokenResponseDto;
import com.diac.awesomehardwaresupply.authentication.dto.UserCredentialsDto;

/**
 * Сервис, отвечающий за авторизацию пользователей
 */
public interface AuthService {

    /**
     * Авторизовать пользователя в системе
     *
     * @param userCredentialsDto DTO с данными авторизации пользователя
     * @return DTO с данными JWT токена
     */
    JwtTokenResponseDto signIn(UserCredentialsDto userCredentialsDto);
}