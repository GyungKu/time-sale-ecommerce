package com.timesale.common.exception.auth;

import com.timesale.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    TOKEN_EMPTY(401, "A001", "인증 토큰이 없습니다."),
    INVALID_TOKEN(401, "A002", "유효하지 않은 토큰입니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
