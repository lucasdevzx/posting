package com.posting.post.config;

import com.posting.post.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

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

}
