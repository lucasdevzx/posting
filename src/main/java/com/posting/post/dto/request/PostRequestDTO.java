package com.posting.post.dto.request;

import java.time.LocalDate;

public record PostRequestDTO(String name,
                             String description,
                             LocalDate date){}
