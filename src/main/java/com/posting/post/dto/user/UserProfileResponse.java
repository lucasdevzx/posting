package com.posting.post.dto.user;

import com.posting.post.entities.AdressUser;
import com.posting.post.entities.User;

public class UserProfileResponse {

    private String name;
    private String email;
    private String country;

    public UserProfileResponse() {
    }

    public UserProfileResponse(User user, AdressUser adressUser) {
        name = user.getName();
        email = user.getEmail();
        country = adressUser.getCountry();
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
}
