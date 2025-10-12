package com.posting.post.dto.response;

public class ProfileDTO {

    private String name;
    private String email;
    private String country;
    private String city;

    public ProfileDTO() {
    }

    public ProfileDTO(String name, String email, String country, String city) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
