package com.diac.awesomehardwaresupply.authentication.model;

import com.diac.awesomehardwaresupply.domain.enumeration.Authority;
import jakarta.persistence.*;
import lombok.*;

/**
 * Модель данных "Привилегия пользователя"
 */
@Entity
@Table(name = "user_authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class UserAuthority {

    /**
     * Идентификатор привилегии
     */
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение привилегии
     */
    @Enumerated(EnumType.STRING)
    private Authority authority;

    /**
     * Пользователь
     */
    @ManyToOne
    @JoinColumn(name = "auth_user_id")
    private User user;
}