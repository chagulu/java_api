package com.example.society.admin.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.society.admin")
public class AdminSocietyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminSocietyApplication.class, args);
    }
}
