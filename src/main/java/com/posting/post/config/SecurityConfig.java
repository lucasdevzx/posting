package com.posting.post.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1. Desativa CSRF temporariamente
                .csrf(csrf -> csrf.disable())

                // 2. Configura autorização de rotas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Rota pública
                        .anyRequest().authenticated()                       // Restante necessita autorização
                )

                // 3, Habilita autenticação básica
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}