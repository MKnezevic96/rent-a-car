package com.rent_a_car.agentski_bekend.dto;

import java.util.Calendar;
import java.util.Date;

public class CarReviewDTO {

    private Integer id;
    private String reviewer;
    private Integer car;
    private Integer rating;
    private String review;
    private Date approvedDate;
    private boolean deleted;
    private boolean approved;


    public CarReviewDTO(Integer id, String reviewer, Integer car, Integer rating, String review, Date approvedDate, boolean deleted, boolean approved) {
        this.id = id;
        this.reviewer = reviewer;
        this.car = car;
        this.rating = rating;
        this.review = review;
        this.approvedDate = approvedDate;
        this.deleted = deleted;
        this.approved = approved;
    }

    public CarReviewDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
