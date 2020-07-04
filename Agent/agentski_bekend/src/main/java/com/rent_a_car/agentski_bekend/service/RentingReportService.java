package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.Pricing;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.RentingReport;
import com.rent_a_car.agentski_bekend.repository.RentingReportRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.RentingReportServiceInterface;
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
