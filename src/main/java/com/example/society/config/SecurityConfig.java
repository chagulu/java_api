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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Public APIs and views
                .requestMatchers(
                    "/auth/**",
                    "/api/auth/register-resident",
                    "/api/admin/login/**",
                    "/api/admin/logout/**",
                    "/api/admin/register-subadmin",
                    "/api/admin/visitors",
                    "/admin/residences",
                    "/api/guest/**",
                    "/admin/residences/**",
                    "/api/visitor/approve",
                    "/api/test/generate",
                    "/user/**",
                    "/admin/visitors",

                    // Admin login & dashboard views
                    "/admin/login",
                    "/admin/dashboard",

                    // Static assets
                    "/admin/css/**",
                    "/admin/js/**",
                    "/admin/images/**",
                    "/images/**",
                    "/css/**",
                    "/js/**",
                    "/webjars/**",
                    "/favicon.ico"
                ).permitAll()

                // Restrict /api/admin/** endpoints to ADMIN role
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Any other requests must be authenticated
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
