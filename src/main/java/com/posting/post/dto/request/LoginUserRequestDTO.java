package com.posting.post.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginUserRequestDTO(

        @NotBlank
        @Email
        String email,

        @Pattern(regexp = "^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%]){7,20}", message = "{User.password.Pattern}")
        String password
) {
}
