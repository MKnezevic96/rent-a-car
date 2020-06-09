package com.admin_service.model;


import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name="car_reviews_table")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class CarReview {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="car_review_id", nullable=false, unique=true)
    private Integer id;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "user_id", nullable=false)
    private User reviewer;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="cars_id", referencedColumnName="cars_id", nullable = false)
    private Cars car;

    @Column(name="rating", nullable=false)
    private Integer rating;

    @Column(name="review", nullable=false)
    private String review;

    @Column(name="approved")
    private Calendar approved;

    @Column(name="deleted", nullable=false)
    private boolean deleted = false;

    public CarReview () {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
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

    public Calendar getApproved() {
        return approved;
    }

    public void setApproved(Calendar approved) {
        this.approved = approved;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
