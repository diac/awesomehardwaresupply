package com.diac.awesomehardwaresupply.authentication.service;

import com.diac.awesomehardwaresupply.authentication.model.User;
import com.diac.awesomehardwaresupply.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

/**
 * Сервис для работы с объектами модели User через JPA
 */
@Service
@RequiredArgsConstructor
public class UserJpaService implements UserService {

    /**
     * Репозиторий для хранения объектов User
     */
    private final UserRepository userRepository;

    /**
     * Объект-шифровальщик паролей
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Добавить нового пользователя в систему
     *
     * @param user Пользователь
     */
    @Override
    public void addUser(User user) {
        user.setPassword(
                passwordEncoder.encode(CharBuffer.wrap(user.getPassword()))
                        .toCharArray()
        );
        userRepository.save(user);
    }
}