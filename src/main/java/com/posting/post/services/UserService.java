package com.posting.post.services;

import java.util.List;
import java.util.Optional;

import com.posting.post.dto.common.PageResponseDTO;
import com.posting.post.dto.request.UserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.User;
import com.posting.post.mapper.UserMapperResponse;
import com.posting.post.repositories.AdressUserRepository;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import static java.util.Arrays.stream;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdressUserRepository adressUserRepository;

    @Autowired
    UserMapperResponse userMapperResponse;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(user));
    }

    public PageResponseDTO<UserResponseDTO> findByName( String name) {
        List<User> users = userRepository.findAllByName(name);
        List<UserResponseDTO> userResponseDTOS = users.stream().map(user -> {
            Optional<AdressUser> adressUser = adressUserRepository.findById(user.getId());
            assert adressUser.orElse(null) != null;
            return userMapperResponse.toDtoUserResponseDTO(user, adressUser.orElse(null));
        }).toList();

        return new PageResponseDTO<>(
                userResponseDTOS,
                0,
                1,
                userResponseDTOS.size(),
                userResponseDTOS.size(),
                true
        );
        }

    public User insert(User obj) {
        userRepository.save(obj);
        return obj;
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
