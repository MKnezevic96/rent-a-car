package com.admin_service.service;

import com.admin_service.model.RentingReport;
import com.admin_service.repository.RentingReportRepository;
import com.admin_service.service.interfaces.RentingReportServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentingReportService implements RentingReportServiceInterface {

    @Autowired
    private RentingReportRepository rentingReportRepository;

    @Override
    public RentingReport save(RentingReport report) {
        return rentingReportRepository.save(report);
    }
}