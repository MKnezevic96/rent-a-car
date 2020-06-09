package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;

@Entity
@Table(name="renting_report_table")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class RentingReport {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="renting_report_id")
    private Integer id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="rr_id", referencedColumnName="rr_id")
    private RentRequest rentingInstance;

    @Column(name="report")
    private String report;

    @Column(name="added_milage", nullable=false)
    private Double addedMileage;

    @Column(name="deleted", nullable=false)
    private boolean deleted;

    public RentingReport() {
        deleted = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RentRequest getRentingInstance() {
        return rentingInstance;
    }

    public void setRentingInstance(RentRequest rentingInstance) {
        this.rentingInstance = rentingInstance;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Double getAddedMileage() {
        return addedMileage;
    }

    public void setAddedMileage(Double addedMileage) {
        this.addedMileage = addedMileage;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
