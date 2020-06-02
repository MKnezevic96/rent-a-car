package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;

@Entity
@Table(name="car_models_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CarModels {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="car_models_id", nullable=false, unique=true)
    private Integer id;

    @Column(name="car_models_name", nullable=false)
    private String name;

    @ManyToOne
    @JoinColumn(name="manufacturer_id", referencedColumnName = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name="car_class_id", referencedColumnName = "car_class_id", nullable = false)
    private CarClass carClass;

    @ManyToOne
    @JoinColumn(name="transmission_type_id", referencedColumnName = "transmission_type_id",nullable = false)
    private TransmissionType transmission;

    @Column
    private boolean deleted;

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
