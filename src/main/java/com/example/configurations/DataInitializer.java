package com.example.configurations;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        return args -> {
            if(userRepository.findByUsername("admin").isPresent()) {
                log.info("Admin user exists");
            } else {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode("123change!me"));
                Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(() -> new RuntimeException("Brak roli USER w bazie!"));
                user.getRoles().add(roleAdmin);
                userRepository.save(user);
                log.info("Admin user created");
            }
        };
    }
}
