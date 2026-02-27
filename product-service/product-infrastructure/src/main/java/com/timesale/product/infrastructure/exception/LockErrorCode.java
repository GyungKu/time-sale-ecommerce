package com.timesale.product.infrastructure.exception;

import com.timesale.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LockErrorCode implements ErrorCode {

    FAILED_ACQUIRE_LOCK(500, "L001", "락 획득에 실패했습니다. 잠시 후 다시 시도해주세요."),
    LOCK_INTERRUPTED(500, "L001", "락 획득 중 인터럽트가 발생했습니다.");

    private final Integer status;
    private final String code;
    private final String message;

}
