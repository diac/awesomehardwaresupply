package com.diac.awesomehardwaresupply.knowledgebase.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
     * Метод-обработчик исключений EntityNotFoundException
     *
     * @param e Объект-исключение
     * @throws IOException В случае, если возникает ошибка записи в response
     */
    @ExceptionHandler(
            value = {
                    EntityNotFoundException.class
            }
    )
    public void handleEntityNotFoundException(Exception e, HttpServletResponse response) throws IOException {
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
     * @param redirectAttributes Объект RedirectAttributes
     * @param bindingResult      Объект BindingResult
     * @return Редирект
     */
    @ExceptionHandler(
            value = {
                    BindException.class
            }
    )
    public String handleValidationBindException(
            Exception e,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult
    ) {
        log.warn(e.getMessage(), e);
        redirectAttributes.addFlashAttribute(
                "errorMessages",
                bindingResult.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        );
        return "redirect:/";
    }
}