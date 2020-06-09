package com.rent_a_car.agentski_bekend.model;

import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "rent_request_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RentRequest implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="rr_id", nullable=false, unique=true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User owningUser;

    @ManyToOne
    @JoinColumn(name="car_id", referencedColumnName = "cars_id")
    private Cars carId;

    @NotNull
    @Column(name="startDate", nullable = false, unique = true)
    private Date startDate;

    @NotNull
    @Column(name="endDate", nullable = false, unique = true)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false, unique = true)
    private RequestStatus status;

    @Column (name="request_group_id")
    private Integer requestGroupId;

    @NotNull
    @Column(name="deleted", nullable = false, unique = true)
    private boolean deleted=false;

    @OneToOne(fetch=FetchType.LAZY)
    private RentingReport rentingReport;

    public RentRequest() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Cars getCarId() {
        return carId;
    }
    public void setCarId(Cars carId) {
        this.carId = carId;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus status) {
        this.status = status;
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

    public Integer getRequestGroupId() {
        return requestGroupId;
    }

    public void setRequestGroupId(Integer requestGroupId) {
        this.requestGroupId = requestGroupId;
    }

    public RentingReport getRentingReport() {
        return rentingReport;
    }

    public void setRentingReport(RentingReport rentingReport) {
        this.rentingReport = rentingReport;
    }
}