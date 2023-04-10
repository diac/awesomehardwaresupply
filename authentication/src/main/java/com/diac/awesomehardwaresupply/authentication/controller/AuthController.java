package com.diac.awesomehardwaresupply.authentication.controller;

import com.diac.awesomehardwaresupply.authentication.model.User;
import com.diac.awesomehardwaresupply.authentication.service.JwtService;
import com.diac.awesomehardwaresupply.authentication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер, ответственный за авторизацию пользователей
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    /**
     * Шаблон сообщения об успешной регистрации пользователя
     */
    private static final String USER_REGISTER_SUCCESS_MESSAGE = "User %s has been successfully registered";

    /**
     * Сервис для работы с объектами модели User
     */
    private final UserService userService;

    /**
     * Сервис для работы с JWT
     */
    private final JwtService jwtService;

    /**
     * Зарегистрировать нового пользователя
     *
     * @param user Данные пользователя для регистрации
     * @return Тело ответа со статусом и сообщением об успешной регистрации
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(
                String.format(USER_REGISTER_SUCCESS_MESSAGE, newUser.getUsername()),
                HttpStatus.CREATED
        );
    }
}