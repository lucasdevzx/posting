package com.posting.post.resources;

import java.net.URI;

import com.posting.post.dto.request.UserRequestDTO;
import com.posting.post.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.User;
import com.posting.post.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/users")
@Resource
public class UserResource {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserResource(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(@RequestParam int page,
                                                         @RequestParam int size) {
        Page<User> users = userService.findAll(page, size);
        return ResponseEntity.ok().body(users.map(userMapper::toDto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userMapper.toDto(userService.findById(id)));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserResponseDTO> findByUser() {
        return ResponseEntity.ok().body(userMapper.toDto(userService.findByUser()));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO obj) {
        var user = userService.createUser(obj);
        ServletUriComponentsBuilder.fromCurrentRequest();
        URI uri = UriComponentsBuilder.fromPath("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(userMapper.toDto(user));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody @Valid User obj) {
        var user = userService.updateUser(obj);
        return ResponseEntity.ok().body(userMapper.toDto(user));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}