package com.posting.post.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1. Desativa CSRF temporariamente
                .csrf(csrf -> csrf.disable())

                // Permite que a tabela do  H2 Console seja carregada em um Frame
                .headers(headers -> headers
                        .frameOptions(frameOptionsConfig -> frameOptionsConfig
                                .sameOrigin()))

                // 2. Configura autorização de rotas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // Libera acesso ao H2 Console
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Rota admin
                        .requestMatchers("/public/**").permitAll()     // Rota pública
                        .anyRequest().authenticated()                           // Restante necessita autorização
                )

                // 3, Habilita autenticação básica
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}