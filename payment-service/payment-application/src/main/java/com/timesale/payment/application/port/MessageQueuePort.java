package com.timesale.payment.application.port;

public interface MessageQueuePort {

    <T> void publish(String topic, T message);

}
