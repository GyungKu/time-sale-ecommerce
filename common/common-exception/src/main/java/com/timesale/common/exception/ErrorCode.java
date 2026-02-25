package com.timesale.common.exception;

public interface ErrorCode {

    Integer getStatus(); // HTTP Status
    String getCode(); // Business Error Code
    String getMessage(); // Response Message

}
