package com.diac.awesomehardwaresupply.authentication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Пользователь"
 */
@Entity
@Table(name = "auth_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя пользователя
     */
    @NotNull(message = "Username is required")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    /**
     * Пароль
     */
    @NotNull(message = "Password is required")
    private char[] password;

    /**
     * Email пользователя
     */
    @NotNull(message = "User email is required")
    @NotBlank(message = "User email cannot be blank")
    private String email;
}