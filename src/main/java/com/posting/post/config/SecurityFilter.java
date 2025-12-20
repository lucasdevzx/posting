package com.posting.post.config;

import com.posting.post.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private AuthConfig authConfig;
    private TokenConfig tokenConfig;

    public SecurityFilter(AuthConfig authConfig, TokenConfig tokenConfig) {
        this.authConfig = authConfig;
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String headerAuthorization = request.getHeader("Authorization");

        if (headerAuthorization != null && headerAuthorization.startsWith("Bearer ")) {
            String token = headerAuthorization.substring(7);
            JWTUserData tokenDecoded = tokenConfig.validToken(token);

            if (tokenDecoded != null) {

                UserDetails user = authConfig.loadUserByUsername(tokenDecoded.email());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
