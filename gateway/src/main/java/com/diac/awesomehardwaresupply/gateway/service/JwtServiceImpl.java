package com.diac.awesomehardwaresupply.gateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.diac.awesomehardwaresupply.domain.enumeration.Authority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<Authority> getAuthoritiesFromToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Claim authorityClaim = decodedJWT.getClaim("authorities");
            return Arrays.stream(authorityClaim.asString().split(","))
                    .filter(authorityString -> !(authorityString.isEmpty() || authorityString.isBlank()))
                    .map(Authority::valueOf)
                    .toList();
    }
}