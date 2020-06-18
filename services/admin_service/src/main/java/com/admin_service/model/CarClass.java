package com.admin_service.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@Entity@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "CarClass", propOrder = {
        "id",
        "name",
        "carModels",
        "deleted"
}, namespace = "nekiUri/car_class")
@Table(name="car_class_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CarClass {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="car_class_id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @Column(name="name", nullable=false, unique=true)
    @XmlElement(required=true)
    private String name;

    @Column(name="deleted", nullable=false)
    @XmlElement(required=true)
    private boolean deleted = false;

    @OneToMany(mappedBy="carClass", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @XmlElement
    private List<CarModels> carModels = new ArrayList<CarModels>();

    public CarClass() {
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

    public List<CarModels> getCarModels() {
        return carModels;
    }

    public void setCarModels(List<CarModels> carModels) {
        this.carModels = carModels;
    }
}
