package com.posting.post.mapper;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.config.UserDetailsImpl;
import com.posting.post.dto.request.UserRequestDTO;
import com.posting.post.dto.response.PermissionsResponseDTO;
import com.posting.post.entities.Coment;
import com.posting.post.entities.UserRole;
import org.springframework.stereotype.Component;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.User;

@Component
public class UserMapper {

    private final AuthenticatedUserService authenticatedUserService;

    public UserMapper(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    public UserResponseDTO toDto(User user) {
        UserDetailsImpl currentUser = authenticatedUserService.getCurrentUserDetails();
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString(),
                new PermissionsResponseDTO(
                        checkEditPermission(user, currentUser),
                        checkDeletePermission(user, currentUser),
                        checkOwnership(user, currentUser)
                ));
    }

    public User toEntity(UserRequestDTO dto) {
        var user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(UserRole.valueOf(dto.role()));
        return user;
    }

    public boolean checkEditPermission(User user, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        return user.getId().equals(currentUser.getUser().getId());
    }

    public boolean checkDeletePermission(User user, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        boolean isOwner = user.getId().equals(currentUser.getUser().getId());
        return isOwner || isAdmin;
    }

    public boolean checkOwnership(User user, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        return user.getId().equals(currentUser.getUser().getId());
    }
}