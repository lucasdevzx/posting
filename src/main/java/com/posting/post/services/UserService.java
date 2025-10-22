package com.posting.post.services;

import java.util.Optional;

import com.posting.post.dto.request.UserRequestDTO;
import com.posting.post.services.exceptions.ConflictException;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.posting.post.entities.User;
import com.posting.post.mapper.UserMapper;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.exceptions.ResourceNotFoundException;

import static java.util.Arrays.stream;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public Page<User> findAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(user));
    }

    public User insert(UserRequestDTO dto) {

        // Futura Refatoração com Código por Email
        User user = userMapper.toEntity(dto);
        boolean exists = userRepository.existsByEmail(user.getEmail());
        if (exists) {
            throw new ConflictException(user.getEmail(), "Email já cadastrado!");
        }

        return userRepository.save(user);
    }

    public User update(Long id, User obj) {
        // Regra de negócio
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new UnauthorizedActionException(id);
        }

        User entity = userRepository.getReferenceById(id);
        updateData(entity, obj);
        return userRepository.save(entity);
    }

    // Auxílio Update
    public void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        if (!user.getId().equals(id)) {
            throw new UnauthorizedActionException(id);
        }
        userRepository.deleteById(id);
    }
}
