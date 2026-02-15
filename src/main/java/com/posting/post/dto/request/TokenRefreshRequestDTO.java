package com.posting.post.dto.request;

import com.posting.post.enums.TokenRefreshEnums;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public record TokenRefreshRequestDTO(

    String tokenRefresh

) {}
