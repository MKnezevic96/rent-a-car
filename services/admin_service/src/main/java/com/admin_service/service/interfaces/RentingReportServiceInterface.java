package com.admin_service.service.interfaces;

import com.admin_service.model.RentingReport;

import java.util.List;

public interface RentingReportServiceInterface {

    List<RentingReport> findAll();
    RentingReport save(RentingReport report);
}
