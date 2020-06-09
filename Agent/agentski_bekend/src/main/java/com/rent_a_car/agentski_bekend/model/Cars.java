package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="cars_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Cars {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="cars_id", nullable=false, unique=true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",  referencedColumnName = "user_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_models_id",  referencedColumnName = "car_models_id",  nullable=false)
    private CarModels model;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fuel_type", referencedColumnName = "id",  nullable=false)
    private FuelType fuelType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pricing_id", referencedColumnName = "pricing_id", nullable=false)
    private Pricing pricing;

    @Column(name="cars_milage", nullable=false)
    private double milage;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="deleted", nullable=false)
    private boolean deleted = false;

    @OneToOne(fetch=FetchType.LAZY)
    private AndroidGPS androidGps;

    @Column(name="town")
    private String town;

    @OneToMany(mappedBy="car", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
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

}
