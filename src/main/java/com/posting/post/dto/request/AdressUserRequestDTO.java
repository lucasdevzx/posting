package com.posting.post.dto.request;

public record AdressUserRequestDTO(
        String country,
        String state,
        String city,
        String neighbourhood,
        String road,
        int houseNumber) {
}
