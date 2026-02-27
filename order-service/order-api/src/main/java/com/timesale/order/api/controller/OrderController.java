package com.timesale.order.api.controller;

import com.timesale.common.security.LoginUser;
import com.timesale.order.api.dto.request.OrderCreateRequest;
import com.timesale.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> placeOrder(
        @LoginUser Long userId,
        @RequestBody OrderCreateRequest request) {
        Long orderId = orderService.placeOrder(
            userId,
            request.productId(),
            request.quantity(),
            request.orderPrice()
        );

        return ResponseEntity.ok(orderId);
    }

}
