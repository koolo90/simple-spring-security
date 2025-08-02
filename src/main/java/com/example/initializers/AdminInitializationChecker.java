package com.example.initializers;

import com.example.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializationChecker {
    private final UserRepository userRepository;

    public AdminInitializationChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean adminExists() {
        return userRepository.existsByRole("ROLE_ADMIN");
    }
}
