package com.posting.post.mapper;

import com.posting.post.dto.request.UserRequestDTO;
import org.springframework.stereotype.Component;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.User;

@Component
public class UserMapper {

    public UserResponseDTO toDto(User user) {
        AdressUser adressUser = user.getAdressUser();
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    public User toEntityResponse(UserResponseDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        return user;
    }

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }
}