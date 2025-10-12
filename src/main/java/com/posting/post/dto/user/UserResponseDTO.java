package com.posting.post.dto.user;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String country;
    private String state;
    private String city;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String name, String email, String country, String state,
            String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
