package com.rent_a_car.agentski_bekend.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "RentingReport", propOrder = {
        "id",
        "rentingInstance",
        "report",
        "addedMileage",
        "deleted"
}, namespace = "nekiUri/renting_report")
@Table(name="renting_report_table")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class RentingReport {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="renting_report_id")
    @XmlElement(required=true)
    private Integer id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="rr_id", referencedColumnName="rr_id")
    @XmlElement
    private RentRequest rentingInstance;

    @Column(name="report")
    @XmlElement
    private String report;

    @Column(name="added_milage", nullable=false)
    @XmlElement(required=true)
    private Double addedMileage;

    @Column(name="deleted", nullable=false)
    @XmlElement(required=true)
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
