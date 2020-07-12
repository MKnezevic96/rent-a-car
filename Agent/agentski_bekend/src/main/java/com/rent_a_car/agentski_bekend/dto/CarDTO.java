package com.rent_a_car.agentski_bekend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class CarDTO {

    private String pricing;
    private String fuelType;
    private String carModel;
    private boolean inCart;

    @Min(0)
    private double milage;

    @NotNull(message = "Name is mandatory")
    @Size(min = 2, max = 30,
            message = "Name must be between 2 and 30 characters long")
    @Pattern(regexp="^$|[a-zA-Z ]+$", message=" must not include special characters.")
    private String name;

    @NotNull(message = "Town is mandatory")
    @Size(min = 2, max = 30,
            message = "Town must be between 2 and 30 characters long")
    @Pattern(regexp="^$|[a-zA-Z ]+$", message="Name must not include special characters.")
    private String town;


    public CarDTO() {
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public double getMilage() {
        return milage;
    }

    public void setMilage(double milage) {
        this.milage = milage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
