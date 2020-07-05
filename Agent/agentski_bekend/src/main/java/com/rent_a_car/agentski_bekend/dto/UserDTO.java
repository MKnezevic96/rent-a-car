package com.rent_a_car.agentski_bekend.dto;

import com.rent_a_car.agentski_bekend.model.User;

public class UserDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String isSelected;
    private String name;
    private String adress;
    private String number;
    private String pib;

    public UserDTO() {
    }

    public UserDTO(String firstname, String lastname, String email, String password, String isSelected, String name, String adress, String number, String pib) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.isSelected = isSelected;
        this.name = name;
        this.adress = adress;
        this.number = number;
        this.pib = pib;

    }

    public UserDTO(User user){
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }
}
