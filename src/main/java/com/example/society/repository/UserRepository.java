package com.example.society.repository;

import com.example.society.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByMobileNo(String mobileNo);
    boolean existsByUsername(String username);
    boolean existsByMobileNo(String mobileNo);
}
