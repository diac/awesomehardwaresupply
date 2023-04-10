package com.diac.awesomehardwaresupply.authentication.config;

import com.diac.awesomehardwaresupply.authentication.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурации безопасности
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    /**
     * Шифровальщик паролей
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Сервис для работы с объектами UserDetails
     */
    private final UserDetailsService userDetailsService;

    /**
     * Конфигурация точки доступа пользователя для обработки исключений
     */
    private final ExceptionHandlingAuthenticationEntryPoint exceptionHandlingAuthenticationEntryPoint;

    /**
     * Сервис для работы с JWT
     */
    private final JwtService jwtService;

    /**
     * Бин-провайдер настроек авторизации
     *
     * @return Бин-провайдер настроек авторизации
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    /**
     * Бин-цепочка безопасности
     *
     * @param httpSecurity Объект HttpSecurity
     * @return Обновленный объект HttpSecurity
     * @throws Exception В случае, если при обработке цепочки было выброшено исключение
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .exceptionHandling().authenticationEntryPoint(exceptionHandlingAuthenticationEntryPoint)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        requests -> requests.requestMatchers(
                                        HttpMethod.POST,
                                        "/auth/register",
                                        "/auth/token",
                                        "/auth/validate"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .build();
    }
}