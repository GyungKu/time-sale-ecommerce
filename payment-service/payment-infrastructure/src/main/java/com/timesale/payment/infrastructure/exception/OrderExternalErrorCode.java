package com.timesale.payment.infrastructure.exception;

import com.timesale.common.exception.ErrorCode;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderExternalErrorCode implements ErrorCode {

    EXTERNAL_SERVER_ERROR(501, "E001", "외부 서버 통신 중 오류가 발생했습니다."),
    NOT_FOUND(404, "O002", "상품을 찾을 수 없습니다.")
    ;

    private final Integer status;
    private final String code;
    private final String message;

    public static Optional<OrderExternalErrorCode> findByCode(String code) {
        return Arrays.stream(values())
            .filter(e -> e.code.equals(code))
            .findFirst();
    }

}
