package com.posting.post.mapper;

import com.posting.post.dto.request.UserRequestDTO;
import com.posting.post.dto.response.UserDetailResponseDTO;
import com.posting.post.dto.response.UserSummaryResponseDTO;
import org.springframework.stereotype.Component;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.User;

@Component
public class UserMapper {

    public UserResponseDTO toDto(User user) {
        AdressUser adressUser = user.getAdressUser();
        return new UserResponseDTO(
                user.getName(),
                user.getEmail());
    }

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

    public UserSummaryResponseDTO toSummary(User user){
        return new UserSummaryResponseDTO(user.getName(), user.getEmail());
    }

    public UserDetailResponseDTO toDetail(User user) {
        return new UserDetailResponseDTO(user.getName(), user.getEmail(), user.getPassword());
    }
}