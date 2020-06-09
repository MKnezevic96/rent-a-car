package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "reciept_table")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Reciept {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="reciept_id", nullable = false, unique = true)
    private Integer id;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private User customer;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private User owner;

    @Column(name="sum")
    private Double sum;

    @OneToMany(mappedBy="reciept", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<RecieptArticle> recieptArticles = new ArrayList<RecieptArticle>();

    @Column(name="deleted", nullable=false)
    private boolean deleted = false;

    public Reciept() {
        this.deleted = false;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public List<RecieptArticle> getRecieptArticles() {
        return recieptArticles;
    }

    public void setRecieptArticles(List<RecieptArticle> recieptArticles) {
        this.recieptArticles = recieptArticles;
    }
}
