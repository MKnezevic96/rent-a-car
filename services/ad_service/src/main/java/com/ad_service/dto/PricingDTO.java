package com.ad_service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PricingDTO {

    @NotNull(message = "Name is mandatory")
    @Size(min = 2, max = 30,
            message = "Name must be between 2 and 30 characters long")
    @Pattern(regexp="^$|[a-zA-Z ]+$", message="Name must not include special characters.")
    private String name;

    @Min(0)
    private double distanceLimit;

    @Min(0)
    private double regularPrice;

    @Min(0)
    private double overusePrice;
    private double collisionDamage;
    private Integer discountDays;

    //    @Pattern(regexp = "^[1-9]{1}[0-9]?(?:\\.\\d{1,2})?$|^0\\.\\d{1,2}?$|100")   // percentage two decimals, except 0
    private double discountPercent;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public PricingDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistanceLimit() {
        return distanceLimit;
    }

    public void setDistanceLimit(double distanceLimit) {
        this.distanceLimit = distanceLimit;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public double getOverusePrice() {
        return overusePrice;
    }

    public void setOverusePrice(double overusePrice) {
        this.overusePrice = overusePrice;
    }

    public double getCollisionDamage() {
        return collisionDamage;
    }

    public void setCollisionDamage(double collisionDamage) {
        this.collisionDamage = collisionDamage;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(Integer discountDays) {
        this.discountDays = discountDays;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }


}
