package com.ad_service.dto;

public class CarModelsDTO {

    private String name;
    private String manufacturer;
    private String carClass;
    private String transmission;
    private boolean deleted;

    public CarModelsDTO() {
    }

    public CarModelsDTO(String name, String manufacturer, String carClass, String transmission, boolean deleted) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.carClass = carClass;
        this.transmission = transmission;
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
