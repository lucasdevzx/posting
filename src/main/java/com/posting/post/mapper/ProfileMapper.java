package com.posting.post.mapper;

import org.springframework.stereotype.Component;
import com.posting.post.dto.response.ProfileDTO;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.User;

@Component
public class ProfileMapper {

    public ProfileDTO toProfileDTO(User user, AdressUser adressUser) {
        ProfileDTO dto = new ProfileDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCountry(adressUser.getCountry());
        dto.setCity(adressUser.getCity());
        return dto;
    }
}
