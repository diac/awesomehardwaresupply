package com.diac.awesomehardwaresupply.gateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с JWT
 */
@Service
public class JwtServiceImpl implements JwtService {

    /**
     * Секретный ключ, используемый при генерации токенов
     */
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    /**
     * Проверить валидность предоставленного токена
     *
     * @param token Токен
     */
    @Override
    public void validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .build();
        verifier.verify(token);
    }
}