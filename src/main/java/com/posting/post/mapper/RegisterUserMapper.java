package com.posting.post.mapper;

import com.posting.post.config.SecurityConfig;
import com.posting.post.dto.request.RegisterUserRequestDTO;
import com.posting.post.dto.response.RegisterUserResponseDTO;
import com.posting.post.entities.User;
import com.posting.post.entities.UserRole;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserMapper {

    private final SecurityConfig securityConfig;

    public RegisterUserMapper(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    public User toEntity(RegisterUserRequestDTO body) {
        User user = new User();
        user.setName(body.name());
        user.setEmail(body.email());
        user.setPassword(securityConfig.passwordEncoder().encode(body.password()));
        user.setRole(UserRole.valueOf(body.role()));
        return user;
    }

    public RegisterUserResponseDTO toRegisterUserResponseDTO(User user) {
        return new RegisterUserResponseDTO(user.getName(), user.getEmail());
    }

}
