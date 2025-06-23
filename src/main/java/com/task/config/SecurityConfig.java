
package com.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // ❗ Tắt CSRF để POST/PUT không bị 401
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // ❗ Mở tất cả API cho dev
            );
        return http.build();
    }
}
