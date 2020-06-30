package com.rent_a_car.agentski_bekend.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequestDTO {

    @NotNull(message = "Name is mandatory")
    @Size(min = 2, max = 30,
            message = "Name must be between 2 and 30 characters long")
    @Pattern(regexp="^$|[a-zA-Z ]+$", message="Name must not include special characters.")
    @SafeHtml
    public String firsname;

    @NotNull(message = "Last name is mandatory")
    @Size(min = 2, max = 32,
            message = "Last Name must be between 2 and 32 characters long")
    @Pattern(regexp="^$|[a-zA-Z ]+$", message="Name must not include special characters.")
    @SafeHtml
    public String lastname;

    @NotNull(message = "Email is mandatory")
    @Email    // hybernate validator
    @SafeHtml
//    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@↵\n" +
//            "(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$")
    public String email;

    @Size(min = 10, max = 500)
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
