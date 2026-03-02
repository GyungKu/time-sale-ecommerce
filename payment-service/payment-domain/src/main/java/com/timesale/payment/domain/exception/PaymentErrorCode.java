package com.timesale.payment.domain.exception;

import com.timesale.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

    NOT_FOUND_BY_ORDER_ID(404, "PAY_001", "주문 ID에 해당하는 결제 정보를 찾을 수 없습니다."),
    INVALID_AMOUNT(400, "PAY_002", "결제 요청 금액이 위변조 되었습니다."),
    ALREADY_PAYMENT(400, "PAY003", "이미 처리된 결제입니다.")
    ;

    private final Integer status;
    private final String code;
    private final String message;
}
