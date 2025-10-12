package com.posting.post.mapper;

import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.entities.AdressUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdressUserMapper {

    public AdressUserResponseDTO toAdressUserResponseDTO(AdressUser obj) {
        AdressUserResponseDTO dto = new AdressUserResponseDTO();
        dto.setCountry(obj.getCountry());
        dto.setState(obj.getState());
        dto.setCity(obj.getCity());
        dto.setNeighborhood(obj.getNeighborhood());
        dto.setHouseNumber(obj.getHouseNumber());
        return dto;

    }
}
