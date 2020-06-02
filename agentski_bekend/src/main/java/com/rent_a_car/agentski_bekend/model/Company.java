package com.rent_a_car.agentski_bekend.model;


import javax.persistence.*;

@Entity
@Table(name = "company_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id", nullable=false, unique=true)
    private Integer id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="name", nullable=false, unique=true)
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="number")
    private String bussinessNumber;

    @Column
    private boolean deleted;

    public Company() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBussinessNumber() {
        return bussinessNumber;
    }

    public void setBussinessNumber(String bussinessNumber) {
        this.bussinessNumber = bussinessNumber;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
