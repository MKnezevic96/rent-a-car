package com.renting_service.service;

import com.renting_service.model.RentingReport;
import com.renting_service.repository.RentingReportRepository;
import com.renting_service.service.interfaces.RentingReportServiceInterface;
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