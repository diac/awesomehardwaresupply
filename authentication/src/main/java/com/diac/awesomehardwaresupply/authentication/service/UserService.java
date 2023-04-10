package com.diac.awesomehardwaresupply.authentication.service;

import com.diac.awesomehardwaresupply.authentication.model.User;

/**
 * Сервис для работы с объектами модели User
 */
public interface UserService {

    /**
     * Добавить нового пользователя в систему
     *
     * @param user Пользователь
     * @return Сохраненный пользователь
     */
    User addUser(User user);
}