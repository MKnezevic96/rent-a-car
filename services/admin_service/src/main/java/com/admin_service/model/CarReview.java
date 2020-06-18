package com.admin_service.model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Calendar;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "CarReview", propOrder = {
        "id",
        "reviewer",
        "car",
        "rating",
        "review",
        "approved",
        "deleted"
}, namespace = "nekiUri/car_review")
@Table(name="car_reviews_table")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class CarReview {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="car_review_id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "user_id", nullable=false)
    @XmlElement
    private User reviewer;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="cars_id", referencedColumnName="cars_id", nullable = false)
    @XmlElement
    private Cars car;

    @Column(name="rating", nullable=false)
    @XmlElement
    private Integer rating;

    @Column(name="review", nullable=false)
    @XmlElement
    private String review;

    @Column(name="approved")
    @XmlElement
    private Calendar approved;

    @Column(name="deleted", nullable=false)
    @XmlElement
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
