package com.softwareplant.api.module.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.*;

@Getter
@Setter
public class ApiErrorResponse {

    private int status;
    private String error;
    private Date timestamp;

    @JsonProperty("message")
    private Map<String, String> messages;

    public ApiErrorResponse(HttpStatus status) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    public ApiErrorResponse() {
        this.messages = new HashMap<>();
        this.timestamp = new Date(System.currentTimeMillis());
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    public void setMessages(String key, String value) {
        this.messages.put(key, value);
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = new Date(timestamp);
    }
}
