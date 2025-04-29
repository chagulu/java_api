package com.example.society;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.example.society.admin.entity",
        "com.example.society.model" // Include the package for User entity
})
@EnableJpaRepositories(basePackages = {
        "com.example.society.admin.repository",
        "com.example.society.repository" // Include the package for UserRepository
})
public class SocietyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocietyApplication.class, args);
    }
}