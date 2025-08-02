package com.example.services;

import com.example.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AdminCheckService {
    private static final Logger log = LogManager.getLogger(AdminCheckService.class);

    private final UserRepository userRepository;

    public AdminCheckService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAdminInitialized() {
        log.info("Checking if admin has been initialized...");
        return userRepository.existsByRole("ROLE_ADMIN");
    }
}
