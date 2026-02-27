package com.timesale.order.infrastructure.exception;

import com.timesale.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExternalErrorCode implements ErrorCode {

    EXTERNAL_SERVER_ERROR(501, "E001", "외부 서버 통신 중 오류가 발생했습니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
