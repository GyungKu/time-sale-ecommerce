package com.timesale.order.domain.exception;

import com.timesale.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    ORDER_SAVE_FAIL(500, "O001", "주문 저장 중 문제가 발생했습니다."),
    NOT_FOUND(404, "O002", "주문을 찾을 수 없습니다."),
    ALREADY_ORDER(400, "O003", "이미 처리된 주문입니다.")
    ;

    private final Integer status;
    private final String code;
    private final String message;

}
