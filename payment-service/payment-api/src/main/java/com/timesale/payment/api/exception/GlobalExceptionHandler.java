package com.timesale.payment.api.exception;

import com.timesale.common.exception.BusinessException;
import com.timesale.common.exception.ErrorCode;
import com.timesale.common.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.warn("BusinessException: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.of(errorCode);

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.warn("BindException: {}", e.getMessage());

        // 에러 1개만 응답
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ErrorResponse response = ErrorResponse.builder()
            .status(400)
            .code("INVALID_INPUT_VALUE")
            .message(errorMessage)
            .build();

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unhandle Exception: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.builder()
            .status(500)
            .code("INTERNAL_SERVER_ERROR")
            .message("서버 내부에 오류가 발생했습니다.")
            .build();

        return ResponseEntity.status(500).body(response);
    }

}
