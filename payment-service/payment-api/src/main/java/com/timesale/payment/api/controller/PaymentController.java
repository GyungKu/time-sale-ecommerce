package com.timesale.payment.api.controller;

import com.timesale.payment.api.dto.request.ConfirmPaymentRequest;
import com.timesale.payment.api.dto.request.PreparePaymentRequest;
import com.timesale.payment.application.PaymentService;
import com.timesale.payment.application.dto.response.PgResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/prepare")
    public ResponseEntity<?> preparePayment(@RequestBody PreparePaymentRequest request) {
        paymentService.preparePayment(request.orderId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<PgResponse> confirmPayment(@RequestBody ConfirmPaymentRequest request) {
        PgResponse response = paymentService.confirmPayment(request.orderId(), request.amount(),
            request.authKey());

        return ResponseEntity.ok(response);
    }

}
