package com.rent_a_car.agentski_bekend.model;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "user_request_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserRequest implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false, unique=true)
    private Integer id;

    @NotNull
    @Column(name="firstname", nullable = false, unique = true)
    private String firstname;

    @NotNull
    @Column(name="lastname", nullable = false, unique = true)
    private String lastname;

    @NotNull
    @Email    // hybernate validator
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 10, max = 16)
    @Column(name="password", nullable = false, unique = true)
    private String password;

    @NotNull
    @Column(name="isCompany")
    private boolean isCompany ;

    @NotNull
    @Column(name="isAgent")
    private boolean isAgent;

    @NotNull
    @Column(name="isCustomer")
    private boolean isCustomer ;

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

    public boolean isCompany() {
        return isCompany;
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }

    public boolean isAgent() {
        return isAgent;
    }

    public void setAgent(boolean agent) {
        isAgent = agent;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }






    public UserRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
