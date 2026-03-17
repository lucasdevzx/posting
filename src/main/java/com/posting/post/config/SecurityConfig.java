package com.posting.post.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final SecurityFilter securityFilter;

  public SecurityConfig(SecurityFilter securityFilter) {
    this.securityFilter = securityFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable())

            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint((request, response, authException) -> {
                      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                      response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    })
            )

        // Permite a exibição da tabela do Banco de Dados H2s
        .headers(headers -> headers
            .frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))

        .authorizeHttpRequests(authRequest -> authRequest
            // H2
            .requestMatchers("/h2-console/**").permitAll()

            // Static Permit
            .requestMatchers(
                "/register.html",
                "/login.html",
                "/post.html",
                "/index.html",
                "/auth.html",
                "/posts.html",
                "/posts-hub.html",
                "/posts-creation.html",
                "/js/**",
                "/css/**",
                "/img/**")
            .permitAll()

            // Auth
            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()

            // Swagger
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**"
                ).permitAll()

            // Post
            .requestMatchers(HttpMethod.GET, "/posts").permitAll()

            // User
            .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")

            // Coment
            .requestMatchers(HttpMethod.GET, "/coments/**").permitAll()

            // Category
            .requestMatchers(HttpMethod.GET, "/categories").permitAll()

            // AdressUser
            .requestMatchers(HttpMethod.GET, "/adress_users").hasRole("ADMIN")

            .anyRequest().authenticated())

        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
