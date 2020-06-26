package com.admin_service.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "FuelType", propOrder = {
        "id",
        "name",
        "cars",
        "deleted"
}, namespace = "nekiUri/fuel_type")
@Entity
@Table(name="fuel_type_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FuelType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @XmlElement
    @Column(name="name", nullable=false, unique=true)
    private String name;

    @XmlElement
    @Column(name="deleted", nullable=false)
    private boolean deleted = false;

    @XmlElement
    @OneToMany(mappedBy="fuelType", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Cars> cars = new ArrayList<Cars>();

    public FuelType() {
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public List<Cars> getCars() {
        return cars;
    }

    public void setCars(List<Cars> cars) {
        this.cars = cars;
    }
}