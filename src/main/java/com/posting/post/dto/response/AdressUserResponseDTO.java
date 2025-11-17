package com.posting.post.dto.response;

public record AdressUserResponseDTO(Long id,
                                    Long userId,
                                    String country,
                                    String city,
                                    String neighbourhood,
                                    String road,
                                    int houseNumber) {}
