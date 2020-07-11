package com.admin_service.dto;

import com.admin_service.model.CarReview;

import java.util.Date;

public class CarReviewDTO {

    private Integer id;
    private Integer reviewerId;
    private Integer carId;
    private Integer rating;
    private String review;
    private Date approvedDate;
    private boolean deleted;
    private boolean approved;
    private String userEmail;

    public CarReviewDTO () {}

    public CarReviewDTO(Integer id, Integer reviewer, Integer car, Integer rating, String review, Date approvedDate, boolean deleted, boolean approved, String userEmail) {
        this.id = id;
        this.reviewerId = reviewer;
        this.carId = car;
        this.rating = rating;
        this.review = review;
        this.approvedDate = approvedDate;
        this.deleted = deleted;
        this.approved = approved;
        this.userEmail = userEmail;
    }

    public CarReviewDTO(CarReview carReview) {
        this.id = carReview.getId();
        this.reviewerId = carReview.getReviewer().getId();
        this.carId = carReview.getCar().getId();
        this.rating = carReview.getRating();
        this.review = carReview.getReview();
        this.approvedDate = carReview.getApprovedDate();
        this.deleted = carReview.isDeleted();
        this.approved = carReview.isApproved();
        this.userEmail = carReview.getReviewer().getEmail();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewer) {
        this.reviewerId = reviewer;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer car) {
        this.carId = car;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
