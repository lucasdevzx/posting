package com.posting.post.resources;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import com.posting.post.dto.common.PageResponseDTO;
import com.posting.post.dto.request.UserRequestDTO;
import com.posting.post.dto.response.UserDetailResponseDTO;
import com.posting.post.dto.response.UserSummaryResponseDTO;
import com.posting.post.mapper.UserMapper;
import jakarta.validation.Valid;
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
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.User;
import com.posting.post.services.UserService;
import jakarta.annotation.Resource;

@RestController
@RequestMapping(value = "/users")
@Resource
public class UserResource {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<User>> findByAll() {
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id,
                                      @RequestParam(name = "view", defaultValue = "detail") String view) {
        User user = userService.findById(id);

        switch (view.toLowerCase(Locale.ROOT)){
            case "summary":
                UserSummaryResponseDTO summaryDTO = userMapper.toSummary(user);
                return ResponseEntity.ok().body(summaryDTO);

            case"detail":
            default:
                UserDetailResponseDTO detailDTO = userMapper.toDetail(user);
                return ResponseEntity.ok().body(detailDTO);
        }
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserRequestDTO obj) {
        UserResponseDTO userResponseDTO = userService.insert(obj);
        ServletUriComponentsBuilder.fromCurrentRequest();
        //URI uri = UriComponentsBuilder.fromPath("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.ok().body(userResponseDTO);
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
