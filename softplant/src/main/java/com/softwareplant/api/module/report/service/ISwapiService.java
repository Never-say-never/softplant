package com.softwareplant.api.module.report.service;

import com.softwareplant.api.module.report.domain.dto.*;
import com.softwareplant.api.module.report.domain.entity.Report;
import com.softwareplant.api.module.report.exception.ReportNotFoundException;

import java.util.List;

public interface ISwapiService {
    List<Report> getReport(Criteria criteria) throws ReportNotFoundException;
}
