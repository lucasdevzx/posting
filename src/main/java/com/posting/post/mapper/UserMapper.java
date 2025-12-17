package com.posting.post.mapper;

import com.posting.post.dto.request.UserRequestDTO;
import org.springframework.stereotype.Component;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.User;

@Component
public class UserMapper {

    public UserResponseDTO toDto(User user) {
        var adressUser = user.getAdressUser();
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString());
    }

    public User toEntity(UserRequestDTO dto) {
        var user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }
}