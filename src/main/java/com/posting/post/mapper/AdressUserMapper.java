package com.posting.post.mapper;

import com.posting.post.dto.request.AdressUserRequestDTO;
import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.entities.AdressUser;
import org.springframework.stereotype.Component;

@Component
public class AdressUserMapper {

    public AdressUserResponseDTO toAdressUser(AdressUser obj) {
        return new AdressUserResponseDTO(
                obj.getId(),
                obj.getUser().getId(),
                obj.getCountry(),
                obj.getCity(),
                obj.getNeighborhood(),
                obj.getRoad(),
                obj.getHouseNumber());
    }

    public AdressUser toEntity(AdressUserRequestDTO dto) {
        AdressUser adressUser = new AdressUser();
        adressUser.setCountry(dto.country());
        adressUser.setState(dto.state());
        adressUser.setCity(dto.city());
        adressUser.setNeighborhood(dto.neighbourhood());
        adressUser.setRoad(dto.road());
        adressUser.setHouseNumber(dto.houseNumber());
        return adressUser;
    }
}