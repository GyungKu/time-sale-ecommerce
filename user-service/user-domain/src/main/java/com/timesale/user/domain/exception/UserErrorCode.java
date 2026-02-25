package com.timesale.user.domain.exception;

import com.timesale.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    DUPLICATE_EMAIL(409, "U001", "이미 존재하는 이메일입니다."),
    USER_NOT_FOUND(404, "U002", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(401, "U003", "아이디 혹은 비밀번호가 틀렸습니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
