package com.rent_a_car.agentski_bekend.dto;

import com.rent_a_car.agentski_bekend.model.RentRequest;


public class RentingReportDTO {

    private RentRequest rentingInstance;

    private String report;
    private Double addedMileage;
    private boolean deleted;

    public RentingReportDTO(){

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
