package com.posting.post.mapper;

import com.posting.post.dto.request.LoginUserRequestDTO;
import com.posting.post.dto.response.LoginUserResponseDTO;
import com.posting.post.entities.User;
import org.springframework.stereotype.Component;

@Component
public class LoginUserMapper {

    public User toEntity(LoginUserRequestDTO body) {
        User user = new User();
        user.setEmail(body.email());
        user.setPassword(body.password());
        return user;
    }
}
