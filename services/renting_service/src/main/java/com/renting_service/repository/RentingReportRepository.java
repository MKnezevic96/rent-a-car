package com.renting_service.repository;

import com.renting_service.model.RentingReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingReportRepository extends JpaRepository<RentingReport, Integer> {
}
