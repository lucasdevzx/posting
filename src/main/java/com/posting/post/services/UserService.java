package com.posting.post.services;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.posting.post.entities.User;
import com.posting.post.repositories.UserRepository;

@Service
public class UserService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }


}
