package com.diac.awesomehardwaresupply.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Сервис для работы с JWT
 */
@Service
public class JwtServiceImpl implements JwtService {

    /**
     * Время жизни генерируемых токенов
     */
    private static final long TOKEN_EXPIRATION_TIME = 1_800_000;

    /**
     * Секретный ключ, используемый при генерации токенов
     */
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    /**
     * Сгенерировать JWT токен
     *
     * @param principal Пользователь-принципал
     * @return Сгенерированный JWT токен
     */
    @Override
    public String createJwtToken(User principal) {
        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(principal.getUsername());
        jwtBuilder.withClaim("username", principal.getUsername());
        jwtBuilder.withClaim(
                "authorities",
                String.join(
                        ",",
                        principal.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )
        );
        return jwtBuilder
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

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