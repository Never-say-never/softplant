package com.softwareplant.api.module.report.exception;

import com.softwareplant.api.module.common.util.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(String message) {
        super(message);
    }

    public ReportNotFoundException() {
        super(CommonConstants.ERROR_REPORT_NOTFOUND);
    }
}
