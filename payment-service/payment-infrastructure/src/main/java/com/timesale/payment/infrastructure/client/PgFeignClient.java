package com.timesale.payment.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "pg-client", url = "${pg.fake.base_url}")
public interface PgFeignClient {

}
