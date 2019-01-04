package com.softwareplant.api.module.report.exception;

import com.softwareplant.api.module.common.util.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class SwapiInvalidResponseException extends RuntimeException {
    public SwapiInvalidResponseException(String message) {
        super(message);
    }

    public SwapiInvalidResponseException() {
        super(CommonConstants.ERROR_SWAPI_INVALIR_JSON);
    }
}
