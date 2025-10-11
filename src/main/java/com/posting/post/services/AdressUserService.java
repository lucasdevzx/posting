package com.posting.post.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.posting.post.entities.AdressUser;
import com.posting.post.repositories.AdressUserRepository;

@Service
public class AdressUserService {

    @Autowired
    AdressUserRepository adressUserRepository;

    public List<AdressUser> findAll() {
        return adressUserRepository.findAll();
    }

}
