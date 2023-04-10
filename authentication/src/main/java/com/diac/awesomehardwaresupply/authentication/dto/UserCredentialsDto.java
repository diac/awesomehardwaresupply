package com.diac.awesomehardwaresupply.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для приема данных авторизации пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsDto {

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Пароль
     */
    private char[] password;
}