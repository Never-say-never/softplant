package com.softwareplant.api.module.report.rest;

import com.softwareplant.api.module.report.domain.dto.*;
import com.softwareplant.api.module.report.domain.entity.SwapiReport;
import com.softwareplant.api.module.report.exception.ReportNotFoundException;
import com.softwareplant.api.module.report.service.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.Thread.sleep;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/report")
public class ReportController {

    @Autowired private IReportService reportService;

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getReport(@RequestBody @Valid Criteria criteria) {
        log.info("Get report, query:" + criteria);
        return this.reportService.findReport(criteria).getReport();
    }

    /**
     *
     * @return
     */
    @RequestMapping(path = "/{reportId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteReport(@PathVariable long reportId) {
        log.info("Delete report, id:" + reportId);
        this.reportService.deleteReport(reportId);
    }

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteReport(@RequestBody @Valid Criteria criteria) {
        log.info("Delete report, id:" + criteria);
        this.reportService.deleteReport(criteria);
    }

    /**
     *
     * @return
     */
    @Async
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void createReport(@RequestBody @Valid Criteria criteria) {
        log.info("Create report, query:" + criteria);
        try {
            this.reportService.createReport(criteria);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("exception: " + e.getMessage());
        }

    }
}
