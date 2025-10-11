package com.posting.post.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
