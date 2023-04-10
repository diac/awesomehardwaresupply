package com.diac.awesomehardwaresupply.authentication.config;

import com.diac.awesomehardwaresupply.authentication.dto.ApiErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Конфигурация точки доступа пользователя для обработки исключений
 */
@Component
@RequiredArgsConstructor
public class ExceptionHandlingAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Маппер объектов
     */
    private final ObjectMapper objectMapper;

    /**
     * Обработчик исключений
     *
     * @param request HTTP-запрос
     * @param response HTTP-ответ
     * @param authException Объект-исключение
     * @throws IOException В случае ошибки ввода-вывода при работе ObjectMapper
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), new ApiErrorDto(authException.getMessage()));
    }
}