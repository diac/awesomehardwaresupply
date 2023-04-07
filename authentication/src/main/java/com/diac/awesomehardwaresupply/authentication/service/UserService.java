package com.diac.awesomehardwaresupply.authentication.service;

import com.diac.awesomehardwaresupply.authentication.model.User;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с объектами модели User
 */
@Service
public interface UserService {

    /**
     * Добавить нового пользователя в систему
     *
     * @param user Пользователь
     */
    void addUser(User user);
}