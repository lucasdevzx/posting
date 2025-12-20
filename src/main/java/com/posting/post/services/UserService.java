package com.posting.post.services;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.dto.request.UserRequestDTO;
import com.posting.post.services.exceptions.ConflictException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.posting.post.entities.User;
import com.posting.post.mapper.UserMapper;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.exceptions.ResourceNotFoundException;

import static java.util.Arrays.stream;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AuthenticatedUserService authenticatedUserService;

    public UserService(UserRepository userRepository, UserMapper userMapper, AuthenticatedUserService authenticatedUserService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> findAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User findById(Long id) {
        // Regra de negócio
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        if (!isAdmin) {
            throw new ResourceNotFoundException("Acesso negado!");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado! ID: " + id));
        return user;
    }

    @PreAuthorize("isAuthenticated()")
    public User findByUser() {
        Long id = authenticatedUserService.getCurrentUserId();
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado! ID: " + id));
        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);

        // Regra de negócio
        boolean exists  = userRepository.existsByEmail(user.getEmail());

        if (exists) {
            throw new ConflictException(user.getEmail(), "Email já cadastrado!");
        }
        return userRepository.save(user);
    }

    @PreAuthorize("isAuthenticated()")
    public User updateUser(User obj) {
        Long userId = authenticatedUserService.getCurrentUserId();
        User entity = userRepository.getReferenceById(userId);

        boolean exists  = userRepository.existsByEmail(entity.getEmail());

        if (exists) {
            throw new ConflictException(entity.getEmail(), "Email já cadastrado!");
        }

        updateData(entity, obj);
        return userRepository.save(entity);
    }

    // Auxílio Update
    public void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        User currentUser = authenticatedUserService.getCurrentUser();
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado! ID: " + id));

        // Regra de negocio
        if(currentUser.getRole().equals(userToDelete.getRole())) {
            throw new ConflictException("Não é permitido deletar um usuário com o mesmo nível de permissão.", "Conflito de permissão");
        }
        userRepository.deleteById(id);
    }

    @PreAuthorize("isAuthenticated()")
    public void deleteUser() {
        Long userId = authenticatedUserService.getCurrentUserId();
        userRepository.deleteById(userId);
    }
}