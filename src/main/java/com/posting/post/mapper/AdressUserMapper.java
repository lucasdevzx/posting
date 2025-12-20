package com.posting.post.mapper;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.config.UserDetailsImpl;
import com.posting.post.dto.request.AdressUserRequestDTO;
import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.dto.response.AuthorResponseDTO;
import com.posting.post.dto.response.PermissionsResponseDTO;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.Coment;
import org.springframework.stereotype.Component;

@Component
public class AdressUserMapper {

    private final AuthenticatedUserService authenticatedUserService;

    public AdressUserMapper(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    public AdressUserResponseDTO toAdressUser(AdressUser obj) {
        UserDetailsImpl currentUser = authenticatedUserService.getCurrentUserDetails();
        return new AdressUserResponseDTO(
                obj.getId(),
                new AuthorResponseDTO(
                        obj.getUser().getId(),
                        obj.getUser().getName()
                ),
                obj.getCountry(),
                obj.getCity(),
                obj.getNeighborhood(),
                obj.getState(),
                obj.getRoad(),
                obj.getHouseNumber(),
                new PermissionsResponseDTO(
                        checkEditPermission(obj, currentUser),
                        checkDeletePermission(obj, currentUser),
                        checkOwnership(obj, currentUser)
                ));
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

    public boolean checkEditPermission(AdressUser adressUser, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        return adressUser.getUser().getId().equals(currentUser.getUser().getId());
    }

    public boolean checkDeletePermission(AdressUser adressUser, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        boolean isOwner = adressUser.getUser().getId().equals(currentUser.getUser().getId());
        return isOwner || isAdmin;
    }

    public boolean checkOwnership(AdressUser adressUser, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        return adressUser.getUser().getId().equals(currentUser.getUser().getId());
    }
}