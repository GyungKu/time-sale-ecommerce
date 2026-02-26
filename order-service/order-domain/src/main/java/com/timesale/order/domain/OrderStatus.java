package com.timesale.order.domain;

public enum OrderStatus {
    PENDING,    // 결제 / 재고 차감 대기
    COMPLETED,  // 주문 완료
    FAILED,     // 재고 부족 등 실패
    CANCELLED,  // 주문 취소
}
