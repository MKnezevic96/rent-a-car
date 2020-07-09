package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.RentingReport;

import java.util.List;

public interface RentingReportServiceInterface {

    List<RentingReport> findAll();
    RentingReport save(RentingReport report);
}
