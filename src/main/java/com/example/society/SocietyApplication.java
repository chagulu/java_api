package com.example.society;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.example.society",
    "com.example.society.admin"
})
public class SocietyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocietyApplication.class, args);
    }
}
