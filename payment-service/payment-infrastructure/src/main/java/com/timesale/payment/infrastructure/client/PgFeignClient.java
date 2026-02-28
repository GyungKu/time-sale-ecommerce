package com.timesale.payment.infrastructure.client;

import com.timesale.payment.infrastructure.dto.request.PgConfirmRequest;
import com.timesale.payment.infrastructure.dto.request.PgPrepareRequest;
import com.timesale.payment.infrastructure.dto.response.PgConfirmResponse;
import com.timesale.payment.infrastructure.dto.response.PgPrepareResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pg-client", url = "http://localhost:8000")
public interface PgFeignClient {

    @PostMapping("/pg/prepare")
    PgPrepareResponse preparePayment(@RequestBody PgPrepareRequest pgPrepareRequest);

    @PostMapping("/pg/confirm")
    PgConfirmResponse confirmPayment(@RequestBody PgConfirmRequest pgConfirmRequest);

}
