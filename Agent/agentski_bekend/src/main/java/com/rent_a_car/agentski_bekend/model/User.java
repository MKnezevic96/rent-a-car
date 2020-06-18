package com.rent_a_car.agentski_bekend.model;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "User", propOrder = {
        "id",
        "firstname",
        "lastname",
        "email",
        "password",
        "role",
        "loginBan",
        "rentBan",
        "messageBan",
        "deleted",
        "company",
        "recieptsIMade",
        "recieptsImOwed",
        "reviews",
        "sentMessages",
        "recieved",
        "pricings"
}, namespace = "nekiUri/user")
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @NotNull
    @Column(name="firstname")
    private String firstname;

    @NotNull
    @Column(name="lastname")
    @XmlElement(required=true)
    private String lastname;
    @NotNull
    @Email    // hybernate validator
    @Column(name="email", nullable = false, unique = true)
    @XmlElement(required=true)
    private String email;

    //@Size(min = 5)
    @Column(name="password", nullable = false)
    @XmlElement(required=true)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @XmlElement
    private Collection<Role> role;

    @Column(name="login_ban")
    @XmlElement
    private Calendar loginBan;

    @Column(name="rent_ban")
    @XmlElement
    private Calendar rentBan;

    @Column(name="messageBan")
    @XmlElement
    private Calendar messageBan;

    @Column (name="deleted", nullable=false)
    @XmlElement(required=true)
    private boolean deleted = false;

    @Column (name="blocked", nullable=false)
    private boolean blocked = false;

    @OneToOne (fetch=FetchType.LAZY)
    @XmlElement
    private Company company;

    @OneToMany(mappedBy="customer", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @XmlElement
    private List<Reciept> recieptsIMade = new ArrayList<Reciept>();

    @OneToMany(mappedBy="owner", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @XmlElement
    private List<Reciept> recieptsImOwed = new ArrayList<Reciept>();

    @OneToMany(mappedBy="reviewer", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @XmlElement
    private List<CarReview> reviews = new ArrayList<CarReview>();

    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @XmlElement
    private List<Message> sentMessages = new ArrayList<Message>();

    @OneToMany(mappedBy = "to", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @XmlElement
    private List<Message> recieved = new ArrayList<Message>();

    @OneToMany(mappedBy="owningUser", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @XmlElement
    private List<Pricing> pricings = new ArrayList<Pricing> ();

    public User() {
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
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

    public void setRecieptsIMade(List<Reciept> recieptsIMade) {
        this.recieptsIMade = recieptsIMade;
    }

    public void setRecieptsImOwed(List<Reciept> recieptsImOwed) {
        this.recieptsImOwed = recieptsImOwed;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getRecieved() {
        return recieved;
    }

    public void setRecieved(List<Message> recieved) {
        this.recieved = recieved;
    }

    public List<Pricing> getPricings() {
        return pricings;
    }

    public void setPricings(List<Pricing> pricings) {
        this.pricings = pricings;
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
