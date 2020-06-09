package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;

@Entity
@Table(name = "pricing_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Pricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pricing_id", nullable=false, unique=true)
    private Integer id;

    @Column(name="pricing_limit", nullable =false)
    private double distanceLimit;

    @Column(name="regular_price", nullable =false)
    private double regularPrice;

    @Column(name="overuse_price", nullable =false)
    private double overusePrice;

    @Column(name="collision_Damage", nullable =false)
    private double collisionDamage;

    @Column(name="discount_days", nullable =false)
    private Integer discountDays;

    @Column(name="discount_percent", nullable =false)
    private double discountPercent;

    @Column(name="pricing_name", nullable =false)
    private String name;

    @Column(name="deleted", nullable=false)
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User owningUser;

    public Pricing() {
        deleted = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User getOwningUser() {
        return owningUser;
    }

    public void setOwningUser(User owningUser) {
        this.owningUser = owningUser;
    }

}
