package com.posting.post.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.posting.post.dto.response.ProfileDTO;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.User;
import com.posting.post.mapper.ProfileMapper;
import com.posting.post.repositories.AdressUserRepository;
import com.posting.post.repositories.UserRepository;

@Service
public class ProfileService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdressUserRepository adressUserRepository;

    @Autowired
    ProfileMapper profileMapper;

    public ProfileDTO fullProfile(Long id) {

        Optional<User> user = userRepository.findById(id);
        Optional<AdressUser> adressUser = adressUserRepository.findById(id);
        return profileMapper.toProfileDTO(user.orElse(null), adressUser.orElse(null));
    }
}
