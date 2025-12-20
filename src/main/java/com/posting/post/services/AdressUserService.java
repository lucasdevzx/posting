package com.posting.post.services;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.dto.request.AdressUserRequestDTO;
import com.posting.post.entities.User;
import com.posting.post.mapper.AdressUserMapper;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.posting.post.entities.AdressUser;
import com.posting.post.repositories.AdressUserRepository;

@Service
public class AdressUserService {

    private final AdressUserRepository adressUserRepository;
    private final UserRepository userRepository;
    private final AdressUserMapper adressUserMapper;

    private final AuthenticatedUserService authenticatedUserService;

    public AdressUserService(AdressUserRepository adressUserRepository,
                             UserRepository userRepository,
                             AdressUserMapper adressUserMapper,
                             AuthenticatedUserService authenticatedUserService

                             ) {
        this.adressUserRepository = adressUserRepository;
        this.userRepository = userRepository;
        this.adressUserMapper = adressUserMapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<AdressUser> findAll(int page, int size) {

        // Regra de negocio
       boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
       if (!isAdmin) {
           throw new UnauthorizedActionException("Access denied. Admins only.");
       }

        var adressUser = adressUserRepository.findAll(PageRequest.of(page, size));
        return adressUser;
    }

    @PreAuthorize("isAuthenticated()")
    public AdressUser findAdressUserByUserId() {
        Long userId = authenticatedUserService.getCurrentUserId();
        var adressUser = adressUserRepository.findByUserId(userId);
        return adressUser;
    }

    @PreAuthorize("isAuthenticated()")
    public AdressUser createAdressUser(AdressUserRequestDTO body) {
        var user = authenticatedUserService.getCurrentUser();

        // Insere o usuario no conttexto gerenciado pelo JPA
        user = userRepository.getReferenceById(user.getId());
        var adressUser = adressUserMapper.toEntity(body);
        adressUser.setUser(user);
        return adressUserRepository.save(adressUser);
    }

    @PreAuthorize("isAuthenticated()")
    public AdressUser updateAdressUser(AdressUserRequestDTO body) {
        var user = authenticatedUserService.getCurrentUser();
        var entity = adressUserRepository.getReferenceById(user.getId());
        user = userRepository.getReferenceById(user.getId());
        var obj = adressUserMapper.toEntity(body);
        updateData(entity, obj);
        entity.setUser(user);
        return adressUserRepository.save(entity);
    }

    public void updateData(AdressUser entity, AdressUser obj) {
        entity.setCountry(obj.getCountry());
        entity.setState(obj.getState());
        entity.setCity(obj.getCity());
        entity.setNeighborhood(obj.getNeighborhood());
        entity.setRoad(obj.getRoad());
        entity.setHouseNumber(obj.getHouseNumber());
    }

    @PreAuthorize("isAuthenticated()")
    public void deleteAdressUser() {
        User user = authenticatedUserService.getCurrentUser();

        // Insere o usuario no conttexto gerenciado pelo JPA
        user = userRepository.getReferenceById(user.getId());
        user.setAdressUser(null);

        // Permite que o JPA remova o endereço do usuário devido ao CascadeType.ALL e orphanRemoval = true
        userRepository.save(user);
    }
}