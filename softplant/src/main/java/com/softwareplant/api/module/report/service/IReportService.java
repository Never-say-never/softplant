package com.softwareplant.api.module.report.service;

import com.softwareplant.api.module.report.domain.dto.Criteria;
import com.softwareplant.api.module.report.domain.entity.SwapiReport;
import com.softwareplant.api.module.report.exception.ReportNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface IReportService {
    SwapiReport findReport(Criteria criteria) throws ReportNotFoundException;
    void createReport(Criteria criteria) throws ReportNotFoundException;
    void deleteReport(long id) throws ReportNotFoundException;

    @Transactional
    void deleteReport(Criteria criteria) throws ReportNotFoundException;
}
