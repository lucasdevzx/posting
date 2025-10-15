package com.posting.post.resources.exceptions;

import java.time.Instant;
import java.util.List;

public class ValidationExceptionStandard {

    private Instant timestamp;
    private Integer status;
    private List<FieldErrorResponse> error;

    public ValidationExceptionStandard(Instant timestamp, Integer status, List<FieldErrorResponse> error) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<FieldErrorResponse> getError() {
        return error;
    }

    public void setError(List<FieldErrorResponse> error) {
        this.error = error;
    }
}
