package com.posting.post.services;

import java.util.Optional;

import com.posting.post.dto.request.AdressUserRequestDTO;
import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.entities.User;
import com.posting.post.mapper.AdressUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.posting.post.entities.AdressUser;
import com.posting.post.repositories.AdressUserRepository;

@Service
public class AdressUserService {

    private final AdressUserRepository adressUserRepository;

    private final UserService userService;

    private final AdressUserMapper adressUserMapper;

    public AdressUserService(AdressUserRepository adressUserRepository, UserService userService,
                             AdressUserMapper adressUserMapper) {
        this.adressUserRepository = adressUserRepository;
        this.userService = userService;
        this.adressUserMapper = adressUserMapper;
    }

    public AdressUser findByUserId(Long id) {
        Optional<User> user = Optional.ofNullable(userService.findById(id));
        var adressUser = adressUserRepository.findByUserId(user.orElseThrow().getId());
        return adressUser;
    }

    public Page<AdressUser> findAll(int page, int size) {
        var adressUser = adressUserRepository.findAll(PageRequest.of(page, size));
        return adressUser;
    }

    public AdressUser createAdressUser(Long userId, AdressUserRequestDTO body) {
        var user = userService.findById(userId);
        var adressUser = adressUserMapper.toEntity(body);
        adressUser.setUser(user);
        return adressUserRepository.save(adressUser);
    }

    public AdressUser updateAdressUser(Long userId, AdressUserRequestDTO body) {
        var entity = adressUserRepository.getReferenceById(userId);
        var obj = adressUserMapper.toEntity(body);
        updateData(entity, obj);
        var user = userService.findById(userId);
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

    public void deleteAdressUser(Long userId) {

        var user = userService.findById(userId);
        var adressUser = findByUserId(user.getId());
        adressUser.setUser(null);
        adressUserRepository.deleteByUserId(userId);
    }
}