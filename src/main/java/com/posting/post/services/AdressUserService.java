package com.posting.post.services;

import java.util.Optional;

import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.entities.User;
import com.posting.post.mapper.AdressUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.posting.post.entities.AdressUser;
import com.posting.post.repositories.AdressUserRepository;

@Service
public class AdressUserService {

    @Autowired
    AdressUserRepository adressUserRepository;

    @Autowired
    UserService userService;

    @Autowired
    AdressUserMapper adressUserMapper;

    public AdressUserResponseDTO findByUserId(Long id) {
        Optional<User> user = Optional.ofNullable(userService.findById(id));
        AdressUser adressUser = adressUserRepository.findByUserId(user.orElseThrow().getId());
        return adressUserMapper.toAdressUserResponseDTO(adressUser);
    }

}
