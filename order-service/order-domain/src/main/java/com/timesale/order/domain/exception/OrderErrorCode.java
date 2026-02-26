package com.timesale.order.domain.exception;

import com.timesale.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    FAIL_ORDER(500, "O001", "테스트");

    private final Integer status;
    private final String code;
    private final String message;

}
