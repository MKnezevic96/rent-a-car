package com.admin_service.dto;

import com.admin_service.model.Cars;

public class CarsListingDTO {

    private String carName;
    private String town;
    private String model;
    private String transmission;
    private String fuel;
    private Integer id;

    public CarsListingDTO(Cars car) {
        this.carName = car.getName();
        this.town = car.getTown();
        this.model = car.getModel().getName();
        this.transmission = car.getModel().getTransmission().getName();
        this.fuel = car.getFuelType().getName();
        this.id = car.getId();
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }
}