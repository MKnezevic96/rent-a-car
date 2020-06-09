package com.admin_service.model;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false, unique=true)
    private Integer id;

    @NotNull
    @Column(name="firstname")
    private String firstname;

    @NotNull
    @Column(name="lastname")
    private String lastname;
    @NotNull
    @Email    // hybernate validator
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Size(min = 5, max = 15)
    @Column(name="password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> role;

    @Column(name="login_ban")
    private Calendar loginBan;

    @Column(name="rent_ban")
    private Calendar rentBan;

    @Column(name="messageBan")
    private Calendar messageBan;

    @Column (name="deleted", nullable=false)
    private boolean deleted = false;

    @OneToOne (fetch=FetchType.LAZY)
    private Company company;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Reciept> recieptsIMade = new ArrayList<Reciept>();

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Reciept> recieptsImOwed = new ArrayList<Reciept>();

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<CarReview> reviews = new ArrayList<CarReview>();

    public User() {
    }

    public List<Reciept> getRecieptsIMade() {
        return recieptsIMade;
    }

    public void setRecieptsIMade(ArrayList<Reciept> recieptsIMade) {
        this.recieptsIMade = recieptsIMade;
    }

    public List<Reciept> getRecieptsImOwed() {
        return recieptsImOwed;
    }

    public void setRecieptsImOwed(ArrayList<Reciept> recieptsImOwed) {
        this.recieptsImOwed = recieptsImOwed;
    }

    public Calendar getLoginBan() {
        return loginBan;
    }

    public void setLoginBan(Calendar loginBan) {
        this.loginBan = loginBan;
    }

    public Calendar getRentBan() {
        return rentBan;
    }

    public void setRentBan(Calendar rentBan) {
        this.rentBan = rentBan;
    }

    public Calendar getMessageBan() {
        return messageBan;
    }

    public void setMessageBan(Calendar messageBan) {
        this.messageBan = messageBan;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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


    public Collection<Role> getRole() {
        return role;
    }

    public Role getRola(){
        ArrayList rols = new ArrayList<>();
        rols = (ArrayList) role;
        return (Role) rols.get(0);}

    public void setRole(Collection<Role> role) {
        this.role = role;
    }


    public List<CarReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<CarReview> reviews) {
        this.reviews = reviews;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
