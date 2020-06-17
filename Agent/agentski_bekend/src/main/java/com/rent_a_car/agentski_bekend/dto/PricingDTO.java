package com.rent_a_car.agentski_bekend.dto;

public class PricingDTO {

    private String name;
    private double distanceLimit;
    private double regularPrice;
    private double overusePrice;
    private double collisionDamage;
    private Integer discountDays;
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
