package com.diac.awesomehardwaresupply.authentication.service;

import com.diac.awesomehardwaresupply.authentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с объектами UserDetails
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Репозиторий для хранения объектов User
     */
    private final UserRepository userRepository;

    /**
     * Получить объект UserDetails по имени пользователя
     *
     * @param username Имя пользователя
     * @return Объект UserDetails для переданного имени пользователя
     * @throws UsernameNotFoundException В случае, если пользователь с переданным на найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(
                        user -> new User(
                                user.getUsername(),
                                String.valueOf(user.getPassword()),
                                user.getAuthorities().stream()
                                        .map(
                                                userAuthority -> new SimpleGrantedAuthority(
                                                        userAuthority.getAuthority().toString())
                                        )
                                        .toList()
                        )
                ).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}