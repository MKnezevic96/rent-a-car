package com.admin_service.dto;

import com.admin_service.model.RentRequest;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class RentRequestDTO {
    private Integer id;
    private String carName;

    @Future
    private Date startDate;

    @Future
    private Date endDate;

    private String status;
    private boolean deleted;
    private Integer carId;
    private Integer owningUserId;
    private Integer requestGroupId;
    private String user;   // user koji zahteva vozilo


    public RentRequestDTO(RentRequest req){
        this.carId=req.getCarId().getId();
        this.startDate=req.getStartDate();
        this.endDate=req.getEndDate();
        this.status=req.getStatus().toString();
        this.deleted=req.isDeleted();
        this.owningUserId=req.getOwningUser().getId();
//        this.requestGroupId=req.getRequestGroupId();
        this.id=req.getId();
        this.carName = req.getCarId().getName();

    }


    public RentRequestDTO() {
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
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

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getOwningUserId() {
        return owningUserId;
    }

    public void setOwningUserId(Integer owningUserId) {
        this.owningUserId = owningUserId;
    }

    public Integer getRequestGroupId() {
        return requestGroupId;
    }

    public void setRequestGroupId(Integer requestGroupId) {
        this.requestGroupId = requestGroupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}