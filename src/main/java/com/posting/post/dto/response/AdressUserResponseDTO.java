package com.posting.post.dto.response;

public record AdressUserResponseDTO(Long id,
                                    AuthorResponseDTO user,
                                    String country,
                                    String city,
                                    String neighbourhood,
                                    String state,
                                    String road,
                                    int houseNumber,
                                    PermissionsResponseDTO permissions) {}
