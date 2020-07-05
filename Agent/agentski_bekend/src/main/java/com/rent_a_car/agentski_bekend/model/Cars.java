package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Cars", propOrder = {
        "id",
        "owner",
        "model",
        "fuelType",
        "pricing",
        "milage",
        "name",
        "androidGps",
        "town",
        "averageRating",
        "reviews",
        "deleted"
}, namespace = "nekiUri/cars")
@Table(name="cars_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Cars {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="cars_id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",  referencedColumnName = "user_id")
    @XmlElement
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_models_id",  referencedColumnName = "car_models_id",  nullable=false)
    @XmlElement(required=true)
    private CarModels model;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fuel_type", referencedColumnName = "id",  nullable=false)
    @XmlElement(required=true)
    private FuelType fuelType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pricing_id", referencedColumnName = "pricing_id", nullable=false)
    @XmlElement(required=true)
    private Pricing pricing;

    @Column(name="cars_milage", nullable=false)
    @XmlElement(required=true)
    private double milage;

    @Column(name="name", nullable=false)
    @XmlElement(required=true)
    private String name;

    @Column(name="deleted", nullable=false)
    @XmlElement(required=true)
    private boolean deleted = false;

    @OneToOne(fetch=FetchType.LAZY)
    @XmlElement
    private AndroidGPS androidGps;

    @Column(name="town")
    @XmlElement
    private String town;

    @Column(name="average_rating")
    @XmlElement
    private String averageRating;

    @Column(name="child_seats")
    @XmlElement
    private String childSeats;

    @OneToMany(mappedBy="car", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @XmlElement
    private List<CarReview> reviews = new ArrayList<CarReview>();


    public Cars() {

    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTown () {
        return town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public AndroidGPS getAndroidGps() {
        return androidGps;
    }

    public void setAndroidGps(AndroidGPS androidGps) {
        this.androidGps = androidGps;
    }

    public CarModels getModel() {
        return model;
    }

    public void setModel(CarModels model) {
        this.model = model;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public double getMilage() {
        return milage;
    }

    public void setMilage(double milage) {
        this.milage = milage;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<CarReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<CarReview> reviews) {
        this.reviews = reviews;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getChildSeats() {
        return childSeats;
    }

    public void setChildSeats(String childSeats) {
        this.childSeats = childSeats;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }
}
