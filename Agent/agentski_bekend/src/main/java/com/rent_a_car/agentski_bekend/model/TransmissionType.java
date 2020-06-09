package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="transmission_type_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TransmissionType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="transmission_type_id", nullable=false, unique=true)
    private Integer id;

    @Column(name="name", nullable=false, unique=true)
    private String name;

    @Column(name="deleted", nullable=false)
    private boolean deleted;

    @OneToMany(mappedBy="transmission", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<CarModels> carModels = new ArrayList<CarModels>();

    public TransmissionType() {
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
