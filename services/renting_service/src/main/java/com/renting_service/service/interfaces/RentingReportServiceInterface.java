package com.renting_service.service.interfaces;

import com.renting_service.model.RentingReport;

import java.util.List;

public interface RentingReportServiceInterface {

    List<RentingReport> findAll();
    RentingReport save(RentingReport report);
}