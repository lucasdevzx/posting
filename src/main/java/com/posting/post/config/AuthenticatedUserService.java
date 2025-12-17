package com.posting.post.config;

import com.posting.post.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

    // Retorna os detalhes do usuário autenticado
    public UserDetailsImpl getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails;
        }
        throw new RuntimeException("Principal não é UserDetails");
    }

    // Retorna a entidade User do usuário autenticado
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null && (auth.getPrincipal() instanceof User)) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUser();
        }
        throw new RuntimeException("Principal não é UserDetails");
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
}
