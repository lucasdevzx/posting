package com.posting.post.mapper;

import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.entities.AdressUser;
import org.springframework.stereotype.Component;

@Component
public class AdressUserMapper {

    public AdressUserResponseDTO toAdressUser(AdressUser obj) {
        return new AdressUserResponseDTO(
                obj.getUser().getId(),
                obj.getCountry(),
                obj.getCity(),
                obj.getNeighborhood(),
                obj.getRoad(),
                obj.getHouseNumber());
    }
}