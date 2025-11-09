package com.build.currency_converter.service;

import com.build.currency_converter.entity.User;
import com.build.currency_converter.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void  registerUser(@Valid User user){
        /// verifica se o email esta em uso
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email já esta em uso");
        }

        //criptografa senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        /// salva o usuario
        userRepository.save(user);
    }
}
