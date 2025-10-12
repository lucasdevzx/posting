package com.posting.post.mapper;

import org.springframework.stereotype.Component;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.User;

@Component
public class UserMapperResponse {

    public UserResponseDTO toDtoUserResponseDTO(User user, AdressUser adressUser) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCountry(adressUser.getCountry());
        dto.setState(adressUser.getState());
        dto.setState(adressUser.getState());
        dto.setCity(adressUser.getCity());
        return dto;
    }
}
