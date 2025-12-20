package com.posting.post.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AdressUserRequestDTO(

        @NotBlank(message = "{AdressUserRequestDTO.country.NotBlank}")
        @Size(max = 99, message = "{AdressUserRequestDTO.country.Size}")
        String country,

        @NotBlank(message = "{AdressUserRequestDTO.state.NotBlank}")
        @Size(max = 99, message = "{AdressUserRequestDTO.state.Size}")
        String state,

        @NotBlank(message = "{AdressUserRequestDTO.city.NotBlank}")
        @Size(max = 99, message = "{AdressUserRequestDTO.city.Size}")
        String city,

        @NotBlank(message = "{AdressUserRequestDTO.neighbourhood.NotBlank}")
        @Size(max = 99, message = "{AdressUserRequestDTO.neighbourhood.Size}")
        String neighbourhood,

        @NotBlank(message = "{AdressUserRequestDTO.road.NotBlank}")
        @Size(max = 99, message = "{AdressUserRequestDTO.road.Size}")
        String road,

        @Positive(message = "{AdressUserRequestDTO.houseNumber.Positive}")
        int houseNumber) {
}
