package com.build.currency_converter.service;

import com.build.currency_converter.entity.User;
import com.build.currency_converter.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void registerUser_Success() {
        User user = new User();
        user.setEmail("teste@example.com");
        user.setPassword("123456");
        user.setName("Teste");
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        when(userRepository.findByEmail("teste@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("hashedPassword");

        userService.registerUser(user);

        verify(userRepository).save(any(User.class));
        // Explicação: Verificamos se o método save() foi chamado com um User.
    }

    @Test
    void registerUser_EmailAlreadyExists_ThrowsException() {
        User user = new User();
        user.setEmail("teste@example.com");

        when(userRepository.findByEmail("teste@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
        // Explicação: Testa se lança exceção quando email já existe.
    }
}