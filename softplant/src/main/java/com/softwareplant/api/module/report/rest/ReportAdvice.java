package com.softwareplant.api.module.report.rest;

import com.softwareplant.api.module.common.dto.ApiErrorResponse;
import com.softwareplant.api.module.report.exception.InvalidCriteriaException;
import com.softwareplant.api.module.report.exception.ReportNotFoundException;
import com.softwareplant.api.module.report.exception.SwapiInvalidResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReportAdvice {

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        ex.getBindingResult().getFieldErrors().forEach(
                e -> response.setMessages(e.getField(), e.getDefaultMessage())
        );

        return response;
    }

    @ResponseBody
    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse reportNotFoundHandler(ReportNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessages("status", ex.getMessage());

        return response;
    }

    @ResponseBody
    @ExceptionHandler(InvalidCriteriaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidCriteriaHandler(InvalidCriteriaException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SwapiInvalidResponseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String swapiServiceInvalidResponse(SwapiInvalidResponseException ex) {
        return ex.getMessage();
    }
}
