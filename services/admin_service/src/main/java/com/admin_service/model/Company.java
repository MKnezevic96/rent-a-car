package com.admin_service.model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Company", propOrder = {
        "id",
        "name",
        "address",
        "bussinessNumber",
        "deleted",
        "owner"}, namespace = "nekiUri/company")
@Table(name = "company_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @Column(name="name", nullable=false, unique=true)
    @XmlElement
    private String name;

    @Column(name="address")
    @XmlElement
    private String address;

    @Column(name="number")
    @XmlElement
    private String bussinessNumber;

    @Column(name="deleted", nullable=false)
    @XmlElement
    private boolean deleted = false;

    @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @XmlElement
    private User owner;

    public Company() {
    }


    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
