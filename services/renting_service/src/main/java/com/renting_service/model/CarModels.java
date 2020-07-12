package com.renting_service.model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "CarModels", propOrder = {
        "id",
        "name",
        "manufacturer",
        "carClass",
        "transmission",
        "deleted"
}, namespace = "nekiUri/car_models")
@Table(name="car_models_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CarModels {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="car_models_id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @Column(name="car_models_name", nullable=false)
    @XmlElement(required=true)
    private String name;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="manufacturer_id", referencedColumnName = "manufacturer_id", nullable = false)
    @XmlElement(required=true)
    private Manufacturer manufacturer;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="car_class_id", referencedColumnName = "car_class_id", nullable = false)
    @XmlElement(required=true)
    private CarClass carClass;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="transmission_type_id", referencedColumnName = "transmission_type_id",nullable = false)
    @XmlElement(required=true)
    private TransmissionType transmission;

    @Column (name="deleted", nullable=false)
    @XmlElement(required=true)
    private boolean deleted = false;

    public CarModels() {
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
