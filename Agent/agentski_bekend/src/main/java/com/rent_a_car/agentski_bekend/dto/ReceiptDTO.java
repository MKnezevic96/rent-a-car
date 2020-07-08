package com.rent_a_car.agentski_bekend.dto;

import com.rent_a_car.agentski_bekend.model.Reciept;
import com.rent_a_car.agentski_bekend.model.RecieptArticle;
import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.model.enums.ServicesEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDTO {

    private String customer;
    private String owner;
    private Double sum;
    private Double dailyRentPrice;
    private Double waiverPrice;
    private Double overusedPrice;
    private String carName;
    private double discount;
    private Integer carId;


    public ReceiptDTO() {}

    public ReceiptDTO(Reciept r) {
        this.customer = r.getCustomer().getEmail();
        this.owner = r.getOwner().getEmail();
        this.sum = r.getSum();

        for (RecieptArticle article: r.getRecieptArticles()) {
            if(article.getService().equals(ServicesEnum.COLISION_DAMAGE_WAIVER)) {
                this.waiverPrice = article.getPrice();
            } else if (article.getService().equals(ServicesEnum.DAILY_RENT)) {
                this.dailyRentPrice = article.getPrice();
            } else {
                this.overusedPrice = article.getPrice();
            }
        }
    }

    public ReceiptDTO(String customer, String owner, Double sum, Double dailyRentPrice, Double waiverPrice, Double overusedPrice, String carName, double discount, Integer carId) {
        this.customer = customer;
        this.owner = owner;
        this.sum = sum;
        this.dailyRentPrice = dailyRentPrice;
        this.waiverPrice = waiverPrice;
        this.overusedPrice = overusedPrice;
        this.carName = carName;
        this.discount = discount;
        this.carId = carId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getDailyRentPrice() {
        return dailyRentPrice;
    }

    public void setDailyRentPrice(Double dailyRentPrice) {
        this.dailyRentPrice = dailyRentPrice;
    }

    public Double getWaiverPrice() {
        return waiverPrice;
    }

    public void setWaiverPrice(Double waiverPrice) {
        this.waiverPrice = waiverPrice;
    }

    public Double getOverusedPrice() {
        return overusedPrice;
    }

    public void setOverusedPrice(Double overusedPrice) {
        this.overusedPrice = overusedPrice;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
