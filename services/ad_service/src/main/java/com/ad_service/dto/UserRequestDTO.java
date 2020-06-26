package com.ad_service.dto;

public class UserRequestDTO {
    public String firsname;
    public String lastname;
    public String email;
    public String password;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String firsname, String lastname, String email, String password) {
        this.firsname = firsname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public String getFirsname() {
        return firsname;
    }

    public void setFirsname(String firsname) {
        this.firsname = firsname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
