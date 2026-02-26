package com.timesale.order.infrastructure.config;

import com.timesale.order.infrastructure.client.ProductFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = ProductFeignClient.class)
public class FeignConfig {

}
