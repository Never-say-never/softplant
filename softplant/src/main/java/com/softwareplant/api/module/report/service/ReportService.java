package com.softwareplant.api.module.report.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareplant.api.module.report.dao.ReportDao;
import com.softwareplant.api.module.report.domain.dto.Criteria;
import com.softwareplant.api.module.report.domain.entity.Report;
import com.softwareplant.api.module.report.domain.entity.SwapiReport;
import com.softwareplant.api.module.report.exception.ReportNotFoundException;
import com.softwareplant.api.module.report.exception.SwapiInvalidResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportService implements IReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ISwapiService swapiService;

    @Override
    public SwapiReport findReport(Criteria criteria) throws ReportNotFoundException {
        return reportDao.findByHash(criteria.getHash())
                .orElseThrow(ReportNotFoundException::new);
    }

    @Transactional
    @Override
    public void createReport(Criteria criteria) throws ReportNotFoundException, SwapiInvalidResponseException {
        if(reportDao.findByHash(criteria.getHash()).isPresent()) {
            log.info("report already exists");
            return;
        }

        List<Report> reports = swapiService.getReport(criteria);
        SwapiReport swapiReport = new SwapiReport();
        swapiReport.setHash(criteria.getHash());

        if(!reports.isEmpty()) {
            final SwapiReport saved = reportDao.save(swapiReport);
            try {
                saved.setReport(
                    new ObjectMapper().writeValueAsString(
                        reports.stream()
                        .map(e -> {
                            e.setReportId(saved.getReportId());
                            return e;
                        })
                        .collect(Collectors.toList())
                    ));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.error("Exception: " + e.getMessage());
                throw new SwapiInvalidResponseException();
            }
        }
        else {
            log.info("no reports created");
        }
    }

    @Transactional
    @Override
    public void deleteReport(long id) throws ReportNotFoundException {
        SwapiReport report = reportDao.findByReportId(id)
                .orElseThrow(ReportNotFoundException::new);
        reportDao.delete(report);
    }

    @Transactional
    @Override
    public void deleteReport(Criteria criteria) throws ReportNotFoundException {
        SwapiReport report = reportDao.findByHash(criteria.getHash())
                .orElseThrow(ReportNotFoundException::new);
        reportDao.delete(report);
    }
}
