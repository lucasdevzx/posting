package com.posting.post.services;

import java.util.List;
import java.util.Optional;

import com.posting.post.dto.common.PageResponseDTO;
import com.posting.post.dto.request.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.User;
import com.posting.post.mapper.UserMapper;
import com.posting.post.repositories.AdressUserRepository;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.exceptions.ResourceNotFoundException;

import static java.util.Arrays.stream;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdressUserRepository adressUserRepository;

    @Autowired
    UserMapper userMapper;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(user));
    }

    public UserResponseDTO insert(UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public User update(Long id, User obj) {
        User entity = userRepository.getReferenceById(id);
        updateData(entity, obj);
        return userRepository.save(entity);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
    }
}
