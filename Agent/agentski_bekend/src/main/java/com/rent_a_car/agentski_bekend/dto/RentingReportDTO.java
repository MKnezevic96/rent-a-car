package com.rent_a_car.agentski_bekend.dto;


import com.rent_a_car.agentski_bekend.model.RentingReport;
import org.hibernate.validator.constraints.SafeHtml;

public class RentingReportDTO {


    private Integer id;
    private Integer rentingInstanceId;
    @SafeHtml
    private String report;
    private double addedMileage;
    private boolean deleted;


    public RentingReportDTO(){}

    public RentingReportDTO(RentingReport report){
        this.id = report.getId();
        this.rentingInstanceId=report.getRentingInstance().getId();
        this.report = report.getReport();
        this.addedMileage = report.getAddedMileage();
        this.deleted = report.isDeleted();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRentingInstanceId() {
        return rentingInstanceId;
    }

    public void setRentingInstanceId(Integer rentingInstanceId) {
        this.rentingInstanceId = rentingInstanceId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public double getAddedMileage() {
        return addedMileage;
    }

    public void setAddedMileage(double addedMileage) {
        this.addedMileage = addedMileage;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
