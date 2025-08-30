package com.app.multillm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll() // Permit all API calls
                .requestMatchers("/h2-console/**").permitAll() // Permit H2 console
                .anyRequest().permitAll() // Permit all other requests for MVP
            )
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())); // for H2 console
        return http.build();
    }
}
