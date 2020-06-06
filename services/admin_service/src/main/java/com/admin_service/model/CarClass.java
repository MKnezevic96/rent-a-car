package com.admin_service.model;

import javax.persistence.*;

@Entity
@Table(name="car_class_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CarClass {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="car_class_id", nullable=false, unique=true)
    private Integer id;

    @Column(name="name", nullable=false, unique=true)
    private String name;

    @Column
    private boolean deleted;

    public CarClass() {
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
