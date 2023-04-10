package com.diac.awesomehardwaresupply.authentication.service;

import com.diac.awesomehardwaresupply.authentication.dto.JwtTokenResponseDto;
import com.diac.awesomehardwaresupply.authentication.dto.UserCredentialsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

/**
 * Сервис, отвечающий за авторизацию пользователей
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * Объект AuthenticationManager
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Сервис для работы с JWT
     */
    private final JwtService jwtService;

    /**
     * Авторизовать пользователя в системе
     *
     * @param userCredentialsDto DTO с данными авторизации пользователя
     * @return DTO с данными JWT токена
     */
    @Override
    public JwtTokenResponseDto signIn(UserCredentialsDto userCredentialsDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCredentialsDto.getUsername(),
                        CharBuffer.wrap(userCredentialsDto.getPassword())
                )
        );
        return new JwtTokenResponseDto(
                jwtService.createJwtToken(
                        (org.springframework.security.core.userdetails.User) authentication.getPrincipal()
                )
        );
    }
}