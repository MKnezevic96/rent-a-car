package com.rent_a_car.agentski_bekend.dto;

import com.rent_a_car.agentski_bekend.model.Cars;

public class CarsDetailsDTO {

    private Integer carId;
    private Integer ownerId;
    private String carName;
    private String carModelName;
    private String carManufacturer;
    private String carClass;
    private String carTransmission;
    private String fuelType;
    private String pricingPlan;
    private Integer pricingId;
    private Double distanceLimit;
    private Double distancePenalty;
    private Double collisionDamage;
    private Integer discoundDays;
    private Double doscountPercent;
    private Double pricePerDay;
    private Double milage;
    private String town;
    private String carOwnerEmail;
    private String averageRating;
    private double mileageNumber;
    private String commentsNumber;
    private String childSeats;

    public CarsDetailsDTO (Cars car) {
        this.carId = car.getId();
        this.ownerId = car.getOwner().getId();
        this.carName = car.getName();
        this.carModelName = car.getModel().getName();
        this.carManufacturer = car.getModel().getManufacturer().getName();
        this.carClass = car.getModel().getCarClass().getName();
        this.carTransmission = car.getModel().getTransmission().getName();
        this.fuelType = car.getFuelType().getName();
        this.pricingPlan = car.getPricing().getName();
        this.pricingId = car.getPricing().getId();
        this.distanceLimit = car.getPricing().getDistanceLimit();
        this.distancePenalty = car.getPricing().getOverusePrice();
        this.collisionDamage = car.getPricing().getCollisionDamage();
        this.discoundDays = car.getPricing().getDiscountDays();
        this.doscountPercent = car.getPricing().getDiscountPercent();
        this.pricePerDay = car.getPricing().getRegularPrice();
        this.milage = car.getMilage();
        this.town = car.getTown();
        this.carOwnerEmail = car.getOwner().getEmail();
        this.averageRating = car.getAverageRating();
        this.childSeats = car.getChildSeats();

    }

    public CarsDetailsDTO() {

    }

    public CarsDetailsDTO(Integer carId, Integer ownerId, String carName, String carModelName, String carManufacturer, String carClass, String carTransmission, String fuelType, String pricingPlan, Integer pricingId, Double distanceLimit, Double distancePenalty, Double collisionDamage, Integer discoundDays, Double doscountPercent, Double pricePerDay, Double milage, String town, String carOwnerEmail, String averageRating, double mileageNumber, String commentsNumber, String childSeats) {
        this.carId = carId;
        this.ownerId = ownerId;
        this.carName = carName;
        this.carModelName = carModelName;
        this.carManufacturer = carManufacturer;
        this.carClass = carClass;
        this.carTransmission = carTransmission;
        this.fuelType = fuelType;
        this.pricingPlan = pricingPlan;
        this.pricingId = pricingId;
        this.distanceLimit = distanceLimit;
        this.distancePenalty = distancePenalty;
        this.collisionDamage = collisionDamage;
        this.discoundDays = discoundDays;
        this.doscountPercent = doscountPercent;
        this.pricePerDay = pricePerDay;
        this.milage = milage;
        this.town = town;
        this.carOwnerEmail = carOwnerEmail;
        this.averageRating = averageRating;
        this.mileageNumber = mileageNumber;
        this.commentsNumber = commentsNumber;
        this.childSeats = childSeats;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getCarManufacturer() {
        return carManufacturer;
    }

    public void setCarManufacturer(String carManufacturer) {
        this.carManufacturer = carManufacturer;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public String getCarTransmission() {
        return carTransmission;
    }

    public void setCarTransmission(String carTransmission) {
        this.carTransmission = carTransmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getPricingPlan() {
        return pricingPlan;
    }

    public void setPricingPlan(String pricingPlan) {
        this.pricingPlan = pricingPlan;
    }

    public Integer getPricingId() {
        return pricingId;
    }

    public void setPricingId(Integer pricingId) {
        this.pricingId = pricingId;
    }

    public Double getDistanceLimit() {
        return distanceLimit;
    }

    public void setDistanceLimit(Double distanceLimit) {
        this.distanceLimit = distanceLimit;
    }

    public Double getDistancePenalty() {
        return distancePenalty;
    }

    public void setDistancePenalty(Double distancePenalty) {
        this.distancePenalty = distancePenalty;
    }

    public Double getCollisionDamage() {
        return collisionDamage;
    }

    public void setCollisionDamage(Double collisionDamage) {
        this.collisionDamage = collisionDamage;
    }

    public Double getDoscountPercent() {
        return doscountPercent;
    }

    public void setDoscountPercent(Double discoundDays) {
        this.doscountPercent = discoundDays;
    }

    public Integer getDiscoundDays() {
        return discoundDays;
    }

    public void setDiscoundDays(Integer discoundDays) {
        this.discoundDays = discoundDays;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Double getMilage() {
        return milage;
    }

    public void setMilage(Double milage) {
        this.milage = milage;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }


    public String getCarOwnerEmail() {
        return carOwnerEmail;
    }

    public void setCarOwnerEmail(String carOwnerEmail) {
        this.carOwnerEmail = carOwnerEmail;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public double getMileageNumber() {
        return mileageNumber;
    }

    public void setMileageNumber(double mileageNumber) {
        this.mileageNumber = mileageNumber;
    }

    public String getCommentsNumber() {
        return commentsNumber;
    }

    public String getChildSeats() {
        return childSeats;
    }

    public void setChildSeats(String childSeats) {
        this.childSeats = childSeats;
    }

    public void setCommentsNumber(String commentsNumber) {
        this.commentsNumber = commentsNumber;
    }
}
