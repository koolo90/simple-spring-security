package com.example.repositories;

import com.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT count(u) > 0 FROM User u JOIN u.roles r WHERE r.name = :role")
    boolean existsByRole(@Param("role") String role);
}
