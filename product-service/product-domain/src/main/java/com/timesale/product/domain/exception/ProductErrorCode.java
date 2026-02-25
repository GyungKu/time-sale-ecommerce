package com.timesale.product.domain.exception;

import com.timesale.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {

    OUT_OF_STOCK(409, "P001", "재고가 부족합니다.");

    private final Integer status;
    private final String code;
    private final String message;

}
