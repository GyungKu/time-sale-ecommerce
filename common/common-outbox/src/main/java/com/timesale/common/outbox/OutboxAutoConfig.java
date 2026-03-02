package com.timesale.common.outbox;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = "com.timesale.common.outbox")
public class OutboxAutoConfig {

}
