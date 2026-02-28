package com.timesale.payment.infrastructure.config;

import com.timesale.payment.infrastructure.client.PgFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = PgFeignClient.class)
public class OpenFeignConfig {

}
