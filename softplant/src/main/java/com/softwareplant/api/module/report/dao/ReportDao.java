package com.softwareplant.api.module.report.dao;

import com.softwareplant.api.module.report.domain.entity.SwapiReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportDao extends JpaRepository<SwapiReport, Long> {
    Optional<SwapiReport> findByReportId(Long id);
    Optional<SwapiReport> findByHash(String hash);
}
