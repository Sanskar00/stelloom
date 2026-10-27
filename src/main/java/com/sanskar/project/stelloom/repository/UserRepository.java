package com.sanskar.project.stelloom.repository;

import com.sanskar.project.stelloom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);   // existsBy, with an "s"
    boolean existsByEmail(String email);
}
