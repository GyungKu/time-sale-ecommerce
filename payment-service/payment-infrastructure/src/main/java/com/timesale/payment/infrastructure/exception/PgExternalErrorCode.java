package com.timesale.payment.infrastructure.exception;

import com.timesale.common.exception.ErrorCode;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PgExternalErrorCode implements ErrorCode {

    EXTERNAL_SERVER_ERROR(501, "E001", "외부 서버 통신 중 오류가 발생했습니다."),
    NOT_FOUND(404, "PG001", "결제 내역을 찾을 수 없습니다."),
    INVALID_AUTH_KEY(401, "PG002", "유효하지 않은 인증 키입니다."),
    NOT_COMPLETED(403, "PG003", "결제 완료된 건이 아닙니다.")
    ;

    private final Integer status;
    private final String code;
    private final String message;

    public static Optional<PgExternalErrorCode> findByStatus(Integer status) {
        return Arrays.stream(values())
            .filter(e -> e.status.equals(status))
            .findFirst();
    }

}
