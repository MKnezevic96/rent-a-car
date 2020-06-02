package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;

@Entity
@Table(name="fuel_type_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FuelType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private Integer id;

    @Column(name="name", nullable=false, unique=true)
    private String name;

    @Column
    private boolean deleted;

    public FuelType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
