package com.posting.post.resources;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.util.UriComponentsBuilder;
import com.posting.post.dto.user.UserResponseDTO;
import com.posting.post.entities.User;
import com.posting.post.services.UserService;
import jakarta.annotation.Resource;

@RestController
@RequestMapping(value = "/users")
@Resource
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findByAll() {
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<UserResponseDTO>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(userService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User obj) {
        User user = userService.insert(obj);
        ServletUriComponentsBuilder.fromCurrentRequest();
        URI uri = UriComponentsBuilder.fromPath("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
        User user = userService.update(id, obj);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
