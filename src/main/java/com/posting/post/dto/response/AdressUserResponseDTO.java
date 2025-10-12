package com.posting.post.dto.response;

public class AdressUserResponseDTO {

    private String country;
    private String state;
    private String city;
    private String neighborhood;
    private String road;
    private int houseNumber;

    public AdressUserResponseDTO() {
    }

    public AdressUserResponseDTO(String country,
                                 String state,
                                 String city,
                                 String neighborhood,
                                 String road,
                                 int houseNumber) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.road = road;
        this.houseNumber = houseNumber;
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

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
}
