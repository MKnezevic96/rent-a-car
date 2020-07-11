package com.ad_service.service.interfaces;


import com.ad_service.model.RentingReport;

import java.util.List;

public interface RentingReportServiceInterface {

    List<RentingReport> findAll();
    RentingReport save(RentingReport report);
}
