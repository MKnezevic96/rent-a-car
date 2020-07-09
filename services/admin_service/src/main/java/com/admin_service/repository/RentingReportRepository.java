package com.admin_service.repository;

import com.admin_service.model.RentingReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingReportRepository extends JpaRepository<RentingReport, Integer> {
}