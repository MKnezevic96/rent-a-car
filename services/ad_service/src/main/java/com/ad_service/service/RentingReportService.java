package com.ad_service.service;

import com.ad_service.model.RentingReport;
import com.ad_service.repository.RentingReportRepository;
import com.ad_service.service.interfaces.RentingReportServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentingReportService implements RentingReportServiceInterface {

    @Autowired
    private RentingReportRepository rentingReportRepository;

    @Override
    public RentingReport save(RentingReport report) {
        return rentingReportRepository.save(report);
    }

    @Override
    public List<RentingReport> findAll() {
        return rentingReportRepository.findAll();
    }
}
