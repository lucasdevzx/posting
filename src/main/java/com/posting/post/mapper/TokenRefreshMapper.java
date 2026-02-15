package com.posting.post.mapper;

import com.posting.post.dto.request.TokenRefreshRequestDTO;
import com.posting.post.dto.response.TokenRefreshResponseDTO;
import com.posting.post.entities.TokenRefresh;
import com.posting.post.enums.TokenRefreshEnums;
import org.springframework.stereotype.Component;

@Component
public class TokenRefreshMapper {

    // toDTO
    public TokenRefreshResponseDTO toDTO(String tokenRefreshHash, String tokenAcessCripto) {
        return new TokenRefreshResponseDTO(
                tokenRefreshHash,
                tokenAcessCripto
        );
    }
}
