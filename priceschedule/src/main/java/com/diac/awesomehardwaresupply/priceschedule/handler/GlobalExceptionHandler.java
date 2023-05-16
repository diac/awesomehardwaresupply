package com.diac.awesomehardwaresupply.priceschedule.handler;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * Глобальный обработчик исключений
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Маппер объектов
     */
    private final ObjectMapper objectMapper;

    /**
     * Метод-обработчик исключений ResourceNotFoundException
     *
     * @param e        Объект-исключение
     * @param response Объект HttpServletResponse
     * @throws IOException В случае, если возникает ошибка записи в response
     */
    @ExceptionHandler(
            value = {
                    ResourceNotFoundException.class
            }
    )
    public void handleResourceNotFoundException(Exception e, HttpServletResponse response) throws IOException {
        log.warn(e.getMessage(), e);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(
                new HashMap<>() {
                    {
                        put("message", e.getMessage());
                        put("type", e.getClass());
                    }
                }
        ));
    }

    /**
     * Метод-обработчик исключений BindException
     *
     * @param e             Объект-исключение
     * @param bindingResult Объект BindingResult
     * @param response      Объект HttpServletResponse
     */
    @ExceptionHandler(
            value = {
                    BindException.class
            }
    )
    public void handleValidationBindException(
            Exception e,
            BindingResult bindingResult,
            HttpServletResponse response
    ) throws IOException {
        log.warn(e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(
                bindingResult.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        ));
    }
}