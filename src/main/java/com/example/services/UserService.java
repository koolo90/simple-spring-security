package com.example.services;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void updateUserRoles(Long userId, Set<String> roleIds) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void updateUserRolesByNames(Long userId, Set<String> roleNames) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<Role> roles = roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void createAdmin(String username, String rawPassword) {
        Role adminRole = roleRepository.findById("ROLE_ADMIN").orElseThrow(
                () -> new RuntimeException("Application initalization failed, 'ROLE_ADMIN' must exist in database.")
        );

        User admin = new User();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(rawPassword));
        admin.setRoles(Set.of(adminRole));
        userRepository.save(admin);
    }
}
