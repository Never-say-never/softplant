package com.softwareplant.api.module.report.exception;

import com.softwareplant.api.module.common.util.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCriteriaException extends RuntimeException {
    public InvalidCriteriaException(String message) {
        super(message);
    }
    public InvalidCriteriaException() {
        super(CommonConstants.ERROR_INVALID_CRITERIA);
    }
}
