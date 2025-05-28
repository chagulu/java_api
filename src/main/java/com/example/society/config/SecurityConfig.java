package com.example.society.config;

import com.example.society.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean(name = "mainFilterChain")
    @Primary
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth

                // 1. Explicitly permit ALL static resources, public APIs, and admin HTML views first
                .requestMatchers(
                    // Static resources (accessible to all)
                    "/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico",
                    "/admin/css/**", "/admin/js/**", "/admin/images/**",

                    // Public APIs (e.g., login)
                    "/auth/**",
                    "/api/admin/login/**",
                    "/api/test/generate",

                    // Admin panel HTML views (accessible to all, as they handle redirects for non-logged-in users)
                    "/admin/login",
                    "/admin/dashboard",
                    "/admin/visitors",
                    "/admin/residences",          // Existing permitted path
                    "/admin/residences/register"  // <-- ADD THIS LINE
                ).permitAll()

                // 2. Define specific authenticated routes with role-based access for APIs
                // Admin APIs (require ADMIN role)
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Mobile User APIs with role-based access
                .requestMatchers(
                    "/user/**",
                    "/api/guest/**",
                    "/api/visitor/approve",
                    "/api/residences"
                ).authenticated()

                // 3. /api/visitor endpoint - authenticated but no specific role required
                .requestMatchers("/api/visitor").authenticated()

                // 4. Everything else must be authenticated
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean(name = "mainPasswordEncoder")
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}