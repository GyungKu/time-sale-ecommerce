package com.timesale.payment.infrastructure.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = {
    "com.timesale.payment",
    "com.timesale.common.outbox"
})
@EnableJpaRepositories(basePackages = {
    "com.timesale.payment",
    "com.timesale.common.outbox"
})
public class JpaConfig {

}
