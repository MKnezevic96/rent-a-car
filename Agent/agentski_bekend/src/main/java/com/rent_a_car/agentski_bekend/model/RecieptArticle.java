package com.rent_a_car.agentski_bekend.model;

import com.rent_a_car.agentski_bekend.model.enums.ServicesEnum;

import javax.persistence.*;


@Entity
@Table(name="reciept_article_table")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class RecieptArticle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="reciept_article_id", nullable=false, unique=true)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="service", nullable = false)
    private ServicesEnum service;

    @Column(name="price", nullable=false)
    private Double price;

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Reciept reciept;

    @Column (name="times", nullable=false)
    private Integer times;

    @Column(name="deleted", nullable=false)
    private boolean deleted = false;

    public RecieptArticle () {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServicesEnum getService() {
        return service;
    }

    public void setService(ServicesEnum service) {
        this.service = service;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Reciept getReciept() {
        return reciept;
    }

    public void setReciept(Reciept reciept) {
        this.reciept = reciept;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
